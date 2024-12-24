package com.printerapp.infrastructure.persistence.mappers;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.user.entities.Customer;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.enums.CustomerType;
import com.printerapp.domain.enums.PaperType;

@Mapper(componentModel = "spring")
public interface InfraCustomerMapper {
        InfraCustomerMapper MAPPER = Mappers.getMapper(InfraCustomerMapper.class);

        @Mappings(value = {
                        @Mapping(source = "id.value", target = "id"),
                        @Mapping(source = "id.value", target = "user.id"),
                        @Mapping(expression = "java(mapNumOfPaper(customer.getPaperQuantities(),PaperType.A3))", target = "numOfPapersA3"),
                        @Mapping(expression = "java(mapNumOfPaper(customer.getPaperQuantities(),PaperType.A4))", target = "numOfPapersA4"),
                        @Mapping(expression = "java(mapNumOfPaper(customer.getPaperQuantities(),PaperType.A5))", target = "numOfPapersA5"),
                        @Mapping(source = "customerId.value", target = "customerId"),
                        @Mapping(source = "customerId.customerType", target = "customerType")
        })
        @Named("mapCustomerPersistence")
        com.printerapp.infrastructure.persistence.models.Customer toPersistence(Customer customer);

        @Mappings(value = {
                        @Mapping(source = "id", target = "id.value"),
                        @Mapping(expression = "java(mapPaperQuanties(model.getNumOfPapersA3(),model.getNumOfPapersA4(),model.getNumOfPapersA5()))", target = "paperQuantities"),
                        @Mapping(expression = "java(mapCustomerId(model.getCustomerId(),model.getCustomerType()))", target = "customerId")
        })
        @Named("mapCustomerDomain")
        Customer toDomain(com.printerapp.infrastructure.persistence.models.Customer model);

        default Integer mapNumOfPaper(Map<PaperType, PaperQuantity> paperQuantities, PaperType paperType) {
                return paperQuantities.get(paperType).getQuantity();
        }

        default Map<PaperType, PaperQuantity> mapPaperQuanties(Integer numOfA3, Integer numOfA4, Integer numOfA5) {
                Map<PaperType, PaperQuantity> paperQuantities = new HashMap<>();
                paperQuantities.put(PaperType.A3, new PaperQuantity(PaperType.A3, numOfA3));
                paperQuantities.put(PaperType.A4, new PaperQuantity(PaperType.A4, numOfA4));
                paperQuantities.put(PaperType.A5, new PaperQuantity(PaperType.A5, numOfA5));
                return paperQuantities;
        }

        default CustomerId mapCustomerId(String customerId, CustomerType customerType) {
                return new CustomerId(customerId,
                                customerType.equals(CustomerType.STUDENT)
                                                ? com.printerapp.domain.enums.CustomerType.STUDENT
                                                : com.printerapp.domain.enums.CustomerType.TEACHER);
        }

}
