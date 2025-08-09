package com.eng.homecare.response;

public record AddressResponseDTO(Long id,
                                 String street,
                                 Integer number,
                                 String neighborhood,
                                 String complement,
                                 String city,
                                 String zipCode,
                                 String fu) {
}
