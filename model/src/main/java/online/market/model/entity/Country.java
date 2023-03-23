package online.market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "countryName")
    private String countryName;

//    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
//    private List<City> cities = new ArrayList<>();
//
//    public void addCity(City city) {
//        cities.add(city);
//        city.setCountry(this);
//    }
//
//    public void removeCity(City city) {
//        cities.remove(city);
//        city.setCountry(null);
//    }
}
