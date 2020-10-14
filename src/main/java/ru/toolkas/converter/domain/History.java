package ru.toolkas.converter.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class History {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private LocalDateTime created;

    @OneToOne
    @JoinColumn(nullable = false)
    private Valute from;

    @Column(nullable = false)
    private BigDecimal fromAmount;

    @OneToOne
    @JoinColumn(nullable = false)
    private Valute to;

    @Column(nullable = false)
    private BigDecimal toAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Valute getFrom() {
        return from;
    }

    public void setFrom(Valute from) {
        this.from = from;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Valute getTo() {
        return to;
    }

    public void setTo(Valute to) {
        this.to = to;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }
}
