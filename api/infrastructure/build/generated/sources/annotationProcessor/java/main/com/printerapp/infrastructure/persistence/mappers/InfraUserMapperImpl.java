package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.infrastructure.persistence.models.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T14:03:30+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class InfraUserMapperImpl implements InfraUserMapper {

    @Autowired
    private InfraCustomerMapper infraCustomerMapper;
    @Autowired
    private InfraEmployeeMapper infraEmployeeMapper;

    @Override
    public User toPersistenct(com.printerapp.domain.aggregates.user.User user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setId( userIdValue( user ) );
        user1.setEmail( userEmailValue( user ) );
        user1.setFirstName( userNameFirstName( user ) );
        user1.setLastName( userNameLastName( user ) );
        user1.setCustomer( infraCustomerMapper.toPersistence( user.getCustomer() ) );
        user1.setEmployee( infraEmployeeMapper.toPersistence( user.getEmployee() ) );
        user1.setCreatedAt( user.getCreatedAt() );
        user1.setUpdatedAt( user.getUpdatedAt() );
        user1.setPhoneNumber( user.getPhoneNumber() );
        user1.setRole( user.getRole() );
        user1.setIsActive( user.getIsActive() );

        return user1;
    }

    @Override
    public com.printerapp.domain.aggregates.user.User toDomain(User model) {
        if ( model == null ) {
            return null;
        }

        com.printerapp.domain.aggregates.user.User user = new com.printerapp.domain.aggregates.user.User();

        user.setId( userToUserId( model ) );
        user.setName( userToUserName( model ) );
        user.setCustomer( infraCustomerMapper.toDomain( model.getCustomer() ) );
        user.setEmployee( infraEmployeeMapper.toDomain( model.getEmployee() ) );
        user.setCreatedAt( model.getCreatedAt() );
        user.setUpdatedAt( model.getUpdatedAt() );
        user.setPhoneNumber( model.getPhoneNumber() );
        user.setRole( model.getRole() );
        user.setIsActive( model.getIsActive() );

        user.setEmail( mapEmail(model.getEmail(),model.getRole()) );

        return user;
    }

    private UUID userIdValue(com.printerapp.domain.aggregates.user.User user) {
        if ( user == null ) {
            return null;
        }
        UserId id = user.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String userEmailValue(com.printerapp.domain.aggregates.user.User user) {
        if ( user == null ) {
            return null;
        }
        Email email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        String value = email.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    private String userNameFirstName(com.printerapp.domain.aggregates.user.User user) {
        if ( user == null ) {
            return null;
        }
        UserName name = user.getName();
        if ( name == null ) {
            return null;
        }
        String firstName = name.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String userNameLastName(com.printerapp.domain.aggregates.user.User user) {
        if ( user == null ) {
            return null;
        }
        UserName name = user.getName();
        if ( name == null ) {
            return null;
        }
        String lastName = name.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    protected UserId userToUserId(User user) {
        if ( user == null ) {
            return null;
        }

        UUID value = null;

        value = user.getId();

        UserId userId = new UserId( value );

        return userId;
    }

    protected UserName userToUserName(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();

        UserName userName = new UserName( firstName, lastName );

        return userName;
    }
}
