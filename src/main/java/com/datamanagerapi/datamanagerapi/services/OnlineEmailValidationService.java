package com.datamanagerapi.datamanagerapi.services;

import com.datamanagerapi.datamanagerapi.Responses.ErrorFeedback;
import com.datamanagerapi.datamanagerapi.Responses.OnlineEmailValidationResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;


@Service
@Slf4j
public class OnlineEmailValidationService
{
    //configurations for rate limiting
    @Value("${LIMIT_FOR_PERIOD}")
    private int limitForPeriod;
    @Value("${LIMIT_REFRESH_PERIOD}")
    private int limitRefreshPeriod;
    @Value("${TIME_OUT_DURATION}")
    private int timeOutDuration;
    @Value("${AUTO_REQUESTS_TO_SEND}")
    private int autoRequestsToSend;
    @Value("${mailapiKey}")
    private String apiKey;

    //configurations for circuit breaker
    @Value("${SLEEP_TIME_BEFORE_CALLING_ENDPOINT}")
    private int waitPeriod;
    @Value("${SLIDING_WINDOW_SIZE}")
    private int slidingWindowSize;
    @Value("${FAILURE_RATE_THRESHOLD}")
    private float failureRateThreshold;
    @Value("${SLOW_CALL_DURATION_THRESHOLD}")
    private int slowCallDurationThreshold;
    @Value("${AUTO_REQUESTS_TO_SEND_CIRCUIT_BREAKER}")
    private int autoRequestsToGenerateForCircuitToBreak;


   public OnlineEmailValidationResponse validateEmailOnline(String email)
   {
       OnlineEmailValidationResponse response  = new OnlineEmailValidationResponse();
       try
       {
           RateLimiterConfig config = RateLimiterConfig.custom()
                   .limitForPeriod(limitForPeriod)
                   .limitRefreshPeriod(Duration.ofSeconds(limitRefreshPeriod))
                   .timeoutDuration(Duration.ofSeconds(timeOutDuration))
                   .build();
           RateLimiterRegistry registry = RateLimiterRegistry.of(config);
           RateLimiter limiter = registry.rateLimiter("onlineEmailValidationService");
           Supplier<OnlineEmailValidationResponse> emailStatusDetails = RateLimiter.decorateSupplier(limiter,() -> CallThirdPartyOnlineMailApi(email));
             for (int counter=0; counter<autoRequestsToSend; counter++)
             {
               try
               {
                   log.info("DateTime: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(Calendar.getInstance().getTime())+"Auto generated request No."+counter+"-->"+emailStatusDetails.get());
               }
               catch (Exception e)
               {
                   response.setStatus("FAILED");
                   response.setMessage("Error ocurred on verifying email,try again ");
                   log.info("Rate limit failure status code : "+e.getMessage());
                   e.printStackTrace();
               }
           }

           log.info("Response from mail api: "+ emailStatusDetails.get());
           if(emailStatusDetails.get().getStatus().equals("SUCCESS"))
           {
               response.setStatus("SUCCESS");
               response.setMessage("Email is valid");
           }
           else
           {
               response.setStatus("FAILED");
               response.setMessage("Invalid email address");
           }
           return response;
       }
       catch(Exception error)
       {
           response.setStatus("FAILED");
           if(error.getMessage().contains("does not permit further calls"))
           {
               response.setMessage("Too many email verification requests at the moment,try again.");
           }
           else
           {
               response.setMessage(error.getMessage());
           }
          return response;

       }
   }

    public OnlineEmailValidationResponse CallThirdPartyOnlineMailApi(String email)
    {
        OnlineEmailValidationResponse response  = new OnlineEmailValidationResponse();
        try
        {
            // call third party endpoint to validate email online
            String emailValidationLink="http://apilayer.net/api/check?access_key="+apiKey+"&email="+email+"&smtp=1&format=1";

            HttpHeaders httpHeaders= new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

            ResponseEntity<OnlineEmailValidationResponse> emailResponse = new RestTemplate().exchange(emailValidationLink, HttpMethod.GET, entity, OnlineEmailValidationResponse.class);
            log.info("Email validation response: "+emailResponse.getBody()+"");
            if(emailResponse.getBody().isFormat_valid()==true)
            {
                response.setStatus("SUCCESS");
                response.setMessage("Email is valid");
            }
            else
            {
                response.setStatus("FAILED");
                response.setMessage("Invalid email supplied");
            }
            return response;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            response.setStatus("FAILED");
            response.setMessage("Error on checking email: ".concat(exception.getMessage()));
            return response;
            }
        }

    public OnlineEmailValidationResponse CallThirdPartyOnlineMailApiWithCircuitBreakerEnabled(String email)
    {
        OnlineEmailValidationResponse response  = new OnlineEmailValidationResponse();
        try
        {
            Thread.sleep(waitPeriod);//create a delay to ensure the circuit breaker opens

            // call third party endpoint to validate email online
            String emailValidationLink="http://apilayer.net/api/check?access_key="+apiKey+"&email="+email+"&smtp=1&format=1";

            HttpHeaders httpHeaders= new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

            ResponseEntity<OnlineEmailValidationResponse> emailResponse = new RestTemplate().exchange(emailValidationLink, HttpMethod.GET, entity, OnlineEmailValidationResponse.class);
            log.info("Email validation response: "+emailResponse.getBody()+"");
            if(emailResponse.getBody().isFormat_valid()==true)
            {
                response.setStatus("SUCCESS");
                response.setMessage("Email is valid");
            }
            else
            {
                response.setStatus("FAILED");
                response.setMessage("Invalid email supplied");
            }
            return response;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            response.setStatus("FAILED");
            response.setMessage("Error on checking email: ".concat(exception.getMessage()));
            return response;
        }
    }

    public OnlineEmailValidationResponse validateEmailOnlineWithCircuitBreakerEnabled(String email)
    {
        OnlineEmailValidationResponse response = new OnlineEmailValidationResponse();

        try
        {
            CircuitBreakerConfig config = CircuitBreakerConfig
                    .custom()
                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                    .slidingWindowSize(slidingWindowSize)
                    .failureRateThreshold(failureRateThreshold)
                    .slowCallDurationThreshold(Duration.ofSeconds(slowCallDurationThreshold))
                    .build();
            CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
            CircuitBreaker circuitBreaker = registry.circuitBreaker("CircuitBreakerEnabledEmailValidationService");
            Supplier<OnlineEmailValidationResponse> emailDetailsSupplier = ()-> CallThirdPartyOnlineMailApiWithCircuitBreakerEnabled(email);
            Supplier<OnlineEmailValidationResponse> decoratedFlightsSupplier = circuitBreaker.decorateSupplier(emailDetailsSupplier);

            //circuits breaks as third party as slow response
            for(int counter=0; counter<autoRequestsToGenerateForCircuitToBreak; counter++) {
                try
                {
                    log.info("DateTime: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(Calendar.getInstance().getTime())+decoratedFlightsSupplier.get()+"");
                } catch (Exception exception)
                {
                    exception.printStackTrace();
                    response.setStatus("FAILED");
                    response.setMessage("Failed to verify email ".concat(exception.getMessage()));
                }
            }
            return decoratedFlightsSupplier.get();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            response.setStatus("FAILED");
            if(exception.getMessage().contains("OPEN and does not permit further calls"))
            {
                response.setMessage("Failed to verify email validity,circuit breaker is open and does not permit further calls");
            }
            else
            {
                response.setMessage("Failed to verify email validity,service is unavailable at the moment");
            }

        }
       return response;
    }
}



