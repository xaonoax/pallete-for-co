package com.palleteforco.palleteforco.domain.member.controller;

import com.palleteforco.palleteforco.domain.cart.dto.CartDto;
import com.palleteforco.palleteforco.domain.cart.service.CartService;
import com.palleteforco.palleteforco.domain.dib.dto.DibDto;
import com.palleteforco.palleteforco.domain.dib.service.DibService;
import com.palleteforco.palleteforco.domain.inquiry.dto.InquiryDto;
import com.palleteforco.palleteforco.domain.inquiry.service.InquiryService;
import com.palleteforco.palleteforco.domain.member.dto.MemberDto;
import com.palleteforco.palleteforco.domain.member.service.MemberService;
import com.palleteforco.palleteforco.domain.orders.dto.OrdersDto;
import com.palleteforco.palleteforco.domain.orders.service.OrdersService;
import com.palleteforco.palleteforco.domain.review.dto.ReviewDto;
import com.palleteforco.palleteforco.domain.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {
    private final MemberService memberService;
    private final InquiryService inquiryService;
    private final CartService cartService;
    private final OrdersService ordersService;
    private final DibService dibService;
    private final ReviewService reviewService;

    @Autowired
    public MyPageController(MemberService memberService,
                            InquiryService inquiryService,
                            CartService cartService,
                            OrdersService ordersService,
                            DibService dibService,
                            ReviewService reviewService) {
        this.memberService = memberService;
        this.inquiryService = inquiryService;
        this.cartService = cartService;
        this.ordersService = ordersService;
        this.dibService = dibService;
        this.reviewService = reviewService;
    }

    // 개인 정보
    @GetMapping("/info")
    public List<MemberDto> getGoogleMemberInfo() throws Exception {
        return memberService.getGoogleMemberInfo();
    }

    // 개인 정보 수정
    @PutMapping("/info")
    public MemberDto modifyMemberInfo(@RequestBody MemberDto memberDto) throws Exception {
        memberService.modifyMemberInfo(memberDto);

        return memberDto;
    }

    // 문의사항 목록
    @GetMapping("/inquiries")
    public List<InquiryDto> getMyInquiries() throws Exception {
        log.info("-------------- getMyInquiries OK --------------");

        return inquiryService.getMyInquiries();
    }

    // 장바구니 목록
    @GetMapping("/cart")
    public List<CartDto> getMyCart() throws Exception {
        return cartService.getMyCart();
    }

    // 구매 목록
    @GetMapping("/orders")
    public List<OrdersDto> getMyOrders() throws Exception {
        return ordersService.getMyOrders();
    }

    // 찜 목록
    @GetMapping("/dib")
    public List<DibDto> getMyDib() throws Exception {
        return dibService.getMyDib();
    }

    // 리뷰 목록
    @GetMapping("/review")
    public List<ReviewDto> getMyReview() throws Exception {
        return reviewService.getMyReview();
    }

}
