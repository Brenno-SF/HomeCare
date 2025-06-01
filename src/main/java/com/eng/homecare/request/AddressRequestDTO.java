package com.eng.homecare.request;

public record AddressRequestDTO(String city,
                                String neighborhood,
                                String zipCode,
                                String complement,
                                Integer number,
                                String street,
                                String fu) {
}
