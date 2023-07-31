package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.BankUserDetails;
import com.datamanagerapi.datamanagerapi.Models.VendorDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorDetails, Long>
{
    @Query(value = "{call GetVendorDetails(:vendorUserName, :vendorPassword)}", nativeQuery = true)

     VendorDetails GetVendorDetails(@Param("vendorUserName") String userName, @Param("vendorPassword") String password);

    @Query(value = "{call GetCustomerDetails(:vendorUserName, :vendorPassword)}", nativeQuery = true)

    VendorDetails GetCustomerDetails(@Param("vendorUserName") String userName, @Param("vendorPassword") String password);

    @Query(value = "{call GetBankStaffDetails(:vendorUserName, :vendorPassword)}", nativeQuery = true)

    VendorDetails GetBankStaffDetails(@Param("vendorUserName") String userName, @Param("vendorPassword") String password);

}
