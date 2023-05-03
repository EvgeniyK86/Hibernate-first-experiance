package hibernate;

import hibernate.entity.Flight;
import hibernate.entity.FlightStatus;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        Flight flight = Flight.builder()
                .flightNo("WE3467")
                .departureDate(LocalDateTime.from(LocalDateTime.of(2000, 1, 19, 01, 00, 00)))
                .departureAirportCode("MNK")
                .arrivalDate(LocalDateTime.from(LocalDateTime.of(2000, 1, 19, 05, 05, 00)))
                .arrivalAirportCode("LDN")
                .aircraftId(1)
                .status(FlightStatus.SCHEDULED)
                .build();
        log.info("Flight object is transient state{}", flight);
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var transaction = session.beginTransaction();
            log.debug("Transaction is created: {}", transaction);

            session.saveOrUpdate(flight);
            log.warn("Flight updated {} in session {}", flight, session);
            session.getTransaction().commit();
        }catch (Exception e) {
            log.error("Exception occured", e);
            throw e;
        }

    }
}
