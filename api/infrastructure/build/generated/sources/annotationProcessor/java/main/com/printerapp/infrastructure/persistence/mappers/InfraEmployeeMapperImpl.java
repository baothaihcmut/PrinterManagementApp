package com.printerapp.infrastructure.persistence.mappers;

import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-23T14:03:30+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.jar, environment: Java 23 (Homebrew)"
)
@Component
public class InfraEmployeeMapperImpl implements InfraEmployeeMapper {

    @Override
    public Employee toPersistence(com.printerapp.domain.aggregates.user.entities.Employee employee) {
        if ( employee == null ) {
            return null;
        }

        Employee employee1 = new Employee();

        employee1.setUser( userIdToUser( employee.getId() ) );
        employee1.setId( employeeIdValue( employee ) );
        employee1.setCreatedAt( employee.getCreatedAt() );
        employee1.setUpdatedAt( employee.getUpdatedAt() );
        employee1.setEmployeeId( employee.getEmployeeId() );
        employee1.setStartWorkDate( employee.getStartWorkDate() );
        employee1.setIsOnWork( employee.getIsOnWork() );

        return employee1;
    }

    @Override
    public com.printerapp.domain.aggregates.user.entities.Employee toDomain(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        String employeeId = null;

        employeeId = employee.getEmployeeId();

        UserId userId = null;

        com.printerapp.domain.aggregates.user.entities.Employee employee1 = new com.printerapp.domain.aggregates.user.entities.Employee( userId, employeeId );

        employee1.setId( employeeToUserId( employee ) );
        employee1.setCreatedAt( employee.getCreatedAt() );
        employee1.setUpdatedAt( employee.getUpdatedAt() );
        employee1.setStartWorkDate( employee.getStartWorkDate() );
        employee1.setIsOnWork( employee.getIsOnWork() );

        return employee1;
    }

    protected User userIdToUser(UserId userId) {
        if ( userId == null ) {
            return null;
        }

        User user = new User();

        user.setId( userId.getValue() );

        return user;
    }

    private UUID employeeIdValue(com.printerapp.domain.aggregates.user.entities.Employee employee) {
        if ( employee == null ) {
            return null;
        }
        UserId id = employee.getId();
        if ( id == null ) {
            return null;
        }
        UUID value = id.getValue();
        if ( value == null ) {
            return null;
        }
        return value;
    }

    protected UserId employeeToUserId(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        UUID value = null;

        value = employee.getId();

        UserId userId = new UserId( value );

        return userId;
    }
}
