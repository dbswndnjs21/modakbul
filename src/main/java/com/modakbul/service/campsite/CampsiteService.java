package com.modakbul.service.campsite;

import com.modakbul.entity.campsite.Campsite;
import com.modakbul.entity.campsite.CampsitePrice;
import com.modakbul.entity.campsite.CampsitePriceId;
import com.modakbul.repository.campsite.CampsitePriceRepository;
import com.modakbul.repository.campsite.CampsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CampsiteService {
    @Autowired
    private CampsiteRepository campsiteRepository;
    @Autowired
    private CampsitePriceRepository campsitePriceRepository;

    public List<Campsite> findByCampgroundId(Long CampgroundId) {
        return campsiteRepository.findByCampgroundId(CampgroundId);
    }

    // 특정 Campground의 Campsite 목록 조회
    public List<Campsite> findCampsitesByCampgroundId(Long campgroundId) {
        return campsiteRepository.findByCampgroundId(campgroundId);
    }

    // Campsite ID로 조회
    public Campsite findCampsiteById(Long id) {
        return campsiteRepository.findById(id).orElse(null);
    }

    // Campsite 저장
    public Campsite saveCampsite(Campsite campsite) {
        Campsite savedCampsite = campsiteRepository.save(campsite);

        // 평일 및 주말 가격 설정 (예시: 다음 30일)
        for (int i = 0; i < 93; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            int price = (date.getDayOfWeek().getValue() <= 5) ? campsite.getWeekdayPrice() : campsite.getWeekendPrice();

            CampsitePrice campsitePrice = CampsitePrice.builder()
                    .id(new CampsitePriceId(savedCampsite.getId(), date))
                    .campsite(savedCampsite)
                    .price(price)
                    .build();

            campsitePriceRepository.save(campsitePrice); // 가격 정보 저장
        }

        return savedCampsite;
    }

    // Campsite 삭제
    public void deleteCampsite(Long id) {
        campsiteRepository.deleteById(id);
    }

    // 특정 캠프사이트와 날짜에 대한 가격 조회
    public List<CampsitePrice> findPricesByCampgroundIdAndDateRange(Long campsiteId, LocalDate checkInDate, LocalDate checkOutDate) {
        return campsitePriceRepository.findByCampsiteIdAndIdPriceDateBetween(campsiteId, checkInDate, checkOutDate.minusDays(1));
    }

    public int calculateTotalPrice(Long campsiteId, LocalDate checkInDate, LocalDate checkOutDate){
        List<CampsitePrice> prices = findPricesByCampgroundIdAndDateRange(campsiteId, checkInDate, checkOutDate);

        return prices.stream()
                .mapToInt(CampsitePrice::getPrice)  // 각 가격을 추출
                .sum();  // 총합 계산
    }

    public int getLowestTotalPrice(Long campsiteId, LocalDate checkInDate, LocalDate checkOutDate){
        return 0;
    }

}
