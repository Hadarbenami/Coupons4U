package com.Coupon4.U.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @ManyToOne
    private Company company;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(unique = true)
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String image;
    @ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Customer> customers;

    public Coupon(Company company, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id= " + id +
                ", company= " + company.getName() +
                ", category= " + category +
                ", title= " + title +
                ", description= " + description +
                ", startDate= " + startDate +
                ", endDate= " + endDate +
                ", amount= " + amount +
                ", price= " + price +
                ", image= " + image +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, category, title, description, startDate, endDate, amount, price, image);
    }


}
