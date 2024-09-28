package com.cuscatlan.orders.shared.mapper;

public interface GenericMapper<E, D> {
    D toDto(E entidad);
    E toEntity(D dto);
}
