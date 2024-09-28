package com.cuscatlan.orders.shared.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract base class for generic mapping between entity and DTO types.
 * 
 * @param <E> the entity type
 * @param <D> the DTO type
 */
public abstract class AbstractGenericMapper<E, D> implements GenericMapper<E, D> {

    @Autowired
    private ModelMapper modelMapper;

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    /**
     * Constructs an AbstractGenericMapper with the specified entity and DTO classes.
     *
     * @param entityClass the class of the entity type
     * @param dtoClass the class of the DTO type
     */
    public AbstractGenericMapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    /**
     * Maps an entity to its corresponding DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    @Override
    public D toDto(E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Maps a DTO to its corresponding entity.
     *
     * @param dto the DTO to map
     * @return the mapped entity
     */
    @Override
    public E toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    /**
     * Maps a list of entities to a list of DTOs.
     *
     * @param entityList the list of entities to map
     * @return the list of mapped DTOs
     */
    public List<D> toDtoList(List<E> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Maps a list of DTOs to a list of entities.
     *
     * @param dtoList the list of DTOs to map
     * @return the list of mapped entities
     */
    public List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
