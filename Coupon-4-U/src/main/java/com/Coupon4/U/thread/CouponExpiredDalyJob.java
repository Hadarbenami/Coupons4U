package com.Coupon4.U.thread;

import com.Coupon4.U.exceptions.IdNotFoundException;
import com.Coupon4.U.models.Coupon;
import com.Coupon4.U.repositories.CouponRepository;
import com.Coupon4.U.services.CompanyService;
import com.Coupon4.U.utils.ServiceUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class CouponExpiredDalyJob{
    private CouponRepository  couponRepository;
    private ApplicationContext context;
    private final int DELAY = 1000 * 60 * 60 * 24; // one day delay

    public CouponExpiredDalyJob(CouponRepository couponRepository, ApplicationContext context) {
        this.couponRepository = couponRepository;
        this.context = context;
    }

    @Scheduled(fixedDelay = DELAY)
    public void deleteExpiredCoupons() throws IdNotFoundException {
            LocalDate currentDate = LocalDate.now();
            for (Coupon coupon : couponRepository.findAll()) {
                if (coupon.getEndDate().isBefore(currentDate)) { // if true there is a need to delete the coupon
                    context.getBean(ServiceUtils.class).deleteCoupon(coupon.getId());
                }
            }

    }

}
