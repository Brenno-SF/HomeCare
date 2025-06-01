package com.eng.homecare.response;

public record AddressResponseDTO(Long id,
                                 String city,
                                 String neighborhood,
                                 String zipCode,
                                 String complement,
                                 Integer number,
                                 String street,
                                 String fu) {
}
