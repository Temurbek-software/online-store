package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.entity.CustomerTariff;
import online.market.model.entity.Tariffs;
import online.market.model.repository.CustomerRepository;
import online.market.model.repository.CustomerTariffRepository;
import online.market.model.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerTariffService {
    private final CustomerTariffRepository customerTariffRepository;

    public void attachTariffToCustomer(Customer customer, Tariffs tariffs) {
        CustomerTariff customerTariff = new CustomerTariff();
        customerTariff.setCustomer(customer);
        customerTariff.setTariffs(tariffs);
        customerTariff.setStartDate(LocalDateTime.now().withSecond(0).withNano(0));
        customerTariff.setEndDate(LocalDateTime.now().plusMonths(1).withSecond(0).withNano(0));
        customerTariff.setTariffs(tariffs);
        customerTariff.setCustomer(customer);
        customerTariffRepository.save(customerTariff);
    }

    public List<CustomerTariff> customerTariffList(long id) {
        return customerTariffRepository.findByCustomerAndTariffs(id);
    }

    public List<CustomerTariff> customerTariffsHistory(long id) {
        return null;
    }
}
