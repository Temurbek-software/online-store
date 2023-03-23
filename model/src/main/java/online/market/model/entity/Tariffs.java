package online.market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tariffs")
public class Tariffs extends BaseEntity
{
    @Column(name = "tariffName")
    private String tariffName;

    @Column(name = "priceFor")
    private String priceFor;

    @Column(name = "description")
    private String description;


}
