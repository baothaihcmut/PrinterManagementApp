package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.customer.value_objects.PaperQuantity;
import com.printerapp.domain.aggregates.user.value_objects.CustomerId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.enums.CustomerType;
import com.printerapp.domain.enums.PaperType;
import com.printerapp.infrastructure.persistence.models.Customer;
import com.printerapp.infrastructure.persistence.models.User;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T14:03:30+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class InfraCustomerMapperImpl implements InfraCustomerMapper {

    @Override
    public Customer toPersistence(com.printerapp.domain.aggregates.user.entities.Customer customer) {
        if ( customer == null ) {
            return null;
        }

        Customer customer1 = new Customer();

        customer1.setUser( userIdToUser( customer.getId() ) );
        customer1.setId( customerIdValue( customer ) );
        customer1.setCustomerId( customerCustomerIdValue( customer ) );
        customer1.setCustomerType( customerCustomerIdCustomerType( customer ) );
        customer1.setCreatedAt( customer.getCreatedAt() );
        customer1.setUpdatedAt( customer.getUpdatedAt() );

        customer1.setNumOfPapersA3( mapNumOfPaper(customer.getPaperQuantities(),PaperType.A3) );
        customer1.setNumOfPapersA4( mapNumOfPaper(customer.getPaperQuantities(),PaperType.A4) );
        customer1.setNumOfPapersA5( mapNumOfPaper(customer.getPaperQuantities(),PaperType.A5) );

        return customer1;
    }

    @Override
    public com.printerapp.domain.aggregates.user.entities.Customer toDomain(Customer model) {
        if ( model == null ) {
            return null;
        }

        CustomerId customerId = mapCustomerId(model.getCustomerId(),model.getCustomerType());
        UserId userId = null;

        com.printerapp.domain.aggregates.user.entities.Customer customer = new com.printerapp.domain.aggregates.user.entities.Customer( userId, customerId );

        customer.setId( customerToUserId( model ) );
        customer.setCreatedAt( model.getCreatedAt() );
        customer.setUpdatedAt( model.getUpdatedAt() );

        if ( customer.getPaperQuantities() != null ) {
            Map<PaperType, PaperQuantity> map = mapPaperQuanties(model.getNumOfPapersA3(),model.getNumOfPapersA4(),model.getNumOfPapersA5());
            if ( map != null ) {
                customer.getPaperQuantities().putAll( map );
            }
        }

        return customer;
    }

    protected User userIdToUser(UserId userId) {
        if ( userId == null ) {
            return null;
        }

        User user = new User();

        user.setId( userId.getValue() );

        return user;
    }

    private UUID customerIdValue(com.printerapp.domain.aggregates.user.entities.Customer customer) {
        if ( customer == null ) {
            return null;
        }
        UserId id = customer.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String customerCustomerIdValue(com.printerapp.domain.aggregates.user.entities.Customer customer) {
        if ( customer == null ) {
            return null;
        }
        CustomerId customerId = customer.getCustomerId();
        if ( customerId == null ) {
            return null;
        }
        String value = customerId.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private CustomerType customerCustomerIdCustomerType(com.printerapp.domain.aggregates.user.entities.Customer customer) {
        if ( customer == null ) {
            return null;
        }
        CustomerId customerId = customer.getCustomerId();
        if ( customerId == null ) {
            return null;
        }
        CustomerType customerType = customerId.getCustomerType();
        if ( customerType == null ) {
            return null;
        }
        return customerType;
    }

    protected UserId customerToUserId(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        UUID value = null;

        value = customer.getId();

        UserId userId = new UserId( value );

        return userId;
    }
}
