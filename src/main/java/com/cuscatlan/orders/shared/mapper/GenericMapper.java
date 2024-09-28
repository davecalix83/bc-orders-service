package com.cuscatlan.orders.shared.mapper;

/**
 * Generic interface for mapping between entity and DTO types.
 *
 * @param <E> the entity type
 * @param <D> the DTO type
 */
public interface GenericMapper<E, D> {
    
    /**
     * Maps an entity to its corresponding DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    D toDto(E entity);

    /**
     * Maps a DTO to its corresponding entity.
     *
     * @param dto the DTO to map
     * @return the mapped entity
     */
    E toEntity(D dto);
}
