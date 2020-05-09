package dishanm.ignite.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
public class Item {
    @QuerySqlField(index = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @QuerySqlField(index = true)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sku", nullable = false)
    private String sku;

    @QuerySqlField
    @JsonInclude()
    @Transient
    private int stockCount;

    @QuerySqlField
    @Column(name = "price", nullable = false)
    private double price;
}
