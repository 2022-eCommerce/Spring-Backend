package com.ecommerce.backend.controller;

import com.ecommerce.backend.MyResponse;
import com.ecommerce.backend.domain.request.AddressRequest;
import com.ecommerce.backend.domain.response.AddressResponse;
import com.ecommerce.backend.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

/** Controller Naming
 * C -> create
 * R -> read
 * U -> modify
 * D -> remove
 */

@Api(tags = {"02. Address"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressApiController {
    private final AddressService addressService;

    @ApiOperation(value = "주소 추가", notes="주소를 추가한다.")
    @PostMapping("/create")
    public MyResponse<Void> create(@RequestBody AddressRequest.RegisterRequest request, Principal principal) {
        final String email = principal.getName();
        log.info("POST /api/addresses/create -> email: {}, request: {}", email, request);

        try {
            addressService.save(request, email);
            return new MyResponse<>(HttpStatus.OK, "주소 추가 성공", null);
        } catch (EntityNotFoundException e) {
            log.warn("POST /api/addresses/create -> email: {}, request: {} EntityNotFoundException", email, request);
            return new MyResponse<>(HttpStatus.NOT_FOUND, "POST FAIL", null);
        }
    }

    @ApiOperation(value = "주소 읽기", notes="주소를 불러온다.")
    @GetMapping("/{addressId}")
    public MyResponse<AddressResponse.ReadResponse> read(@PathVariable Long addressId) {
        log.info("GET /api/addresses/{addressId} addressId: {}", addressId);
        try {
            return new MyResponse<>(HttpStatus.OK, "GET SUCCESS", addressService.findById(addressId));
        } catch (EntityNotFoundException e) {
            log.warn("GET /api/addresses/{addressId} addressId: {} EntityNotFoundException", addressId);
            return new MyResponse<>(HttpStatus.NOT_FOUND, "GET FAIL", null);
        }
    }

    @ApiOperation(value = "주소 수정", notes="주소를 수정한다.")
    @PatchMapping("/update/{addressId}")
    public MyResponse<Void> update(@PathVariable Long addressId, @RequestBody AddressRequest.UpdateRequest request){
        log.info("PATCH /api/addresses/update/{addressId} -> request: {}", request);
        try {
            addressService.update(addressId, request);
            return new MyResponse<>(HttpStatus.OK, "GET SUCCESS", null);
        } catch (EntityNotFoundException e) {
            log.warn("PATCH /api/addresses/{addressId} -> request: {} EntityNotFoundException", request);
            return new MyResponse<>(HttpStatus.NOT_FOUND, "PATCH FAIL", null);
        }
    }

    @ApiOperation(value = "주소 삭제", notes = "주소 정보를 삭제한다.")
    @DeleteMapping("/{addressId}")
    public MyResponse<AddressResponse.DeleteResponse> remove(@PathVariable Long addressId){
        try {
            log.info("DELETE /api/addresses/{addressId} -> addressId: {}", addressId);
            return new MyResponse<>(HttpStatus.OK, "DELETE SUCCESS", addressService.delete(addressId));
        } catch (EntityNotFoundException e) {
            return new MyResponse<>(HttpStatus.OK, "DELETE FAIL", null);
        }
    }
}