package com.datamanagerapi.datamanagerapi.DataAccessObjects;
import com.datamanagerapi.datamanagerapi.Models.SystemUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserDetailsRepository extends JpaRepository<SystemUserDetails, Long>
{
    @Query(value = "{call GetSystemUserDetails(:vendorUserName, :vendorPassword)}", nativeQuery = true)

     SystemUserDetails GetSystemUserDetails(@Param("vendorUserName") String userName, @Param("vendorPassword") String password);


}
