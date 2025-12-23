package k23cnt3.nguyencongtung.project3;

import vn.payos.PayOS;
// Import các class mới của bản 2.x
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem; // <-- Class mới bạn vừa tìm thấy

public class TestPayOS {
    public static void main(String[] args) {
        String clientId = "ef278d93-dc87-492b-80e5-50a31ca580b6";
        String apiKey = "87b589f1-048a-44bc-97ec-e7b886342b34";
        String checksumKey = "ef3972c57a254e7ae93d1f245df4b4d229d8bf665a9ff778ffef515d1f154ca1";

        PayOS payOS = new PayOS(clientId, apiKey, checksumKey);

        long orderCode = System.currentTimeMillis() / 1000;

        // 1. Dùng đúng class PaymentLinkItem
        PaymentLinkItem item = PaymentLinkItem.builder()
                .name("Mì tôm test")
                .quantity(1)
                .price(2000L)
                .build();

        // 2. Tạo Request
        CreatePaymentLinkRequest requestBody = CreatePaymentLinkRequest.builder()
                .orderCode(orderCode)
                .amount(2000L)
                .description("Thanh toan don hang")
                .returnUrl("https://google.com")
                .cancelUrl("https://google.com")
                // Nếu builder có hỗ trợ số ít thì dùng .item(), nếu không thì dùng .items(List.of(item))
                .item(item)
                .build();

        try {
            // 3. Gọi API
            CreatePaymentLinkResponse response = payOS.paymentRequests().create(requestBody);

            System.out.println("-------------------------------------------");
            System.out.println("TẠO LINK THÀNH CÔNG!");
            System.out.println("Link thanh toán: " + response.getCheckoutUrl());
            System.out.println("-------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}