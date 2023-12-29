import { Component, Renderer2, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-your-component',
  template: '<div #contentContainer></div>',
})
export class PDFInvoiceComponent implements AfterViewInit {
  @ViewChild('contentContainer', { static: true }) contentContainer: ElementRef;
  
  constructor(private renderer: Renderer2) {}

  ngAfterViewInit() {
    // Đoạn mã HTML cần truyền
    const htmlContent = `<div class="p-10">
    <!--Logo and Other info-->
    <div class="flex items-start justify-center">
        <div class="flex-1">
            <div class="w-60 pb-6">
                <img class="w-40" src="https://upload.wikimedia.org/wikipedia/en/7/78/Post_Office_Logo.svg" alt="Logo">
            </div>

            <div class="w-60 pl-4 pb-7">
                <h3 class="font-bold">Họ tên địa chỉ người gửi:</h3>
                <p>Số nhà 31, ngách 63/1, Dịch Vọng Hậu, Cầu Giấy, Hà Nội</p>
            </div>
            <div class="w-60 pl-4 pb-7">
                <h3 class="font-bold">Số điện thoại người gửi:</h3>
                <p>0987654321</p>
            </div>
            <div class="w-60 pl-4 pb-6">
                <h3 class="font-bold">Bưu Cục gửi:</h3>
                <p>Cầu Giấy - Hà Nội</p>
            </div>

            <!-- <div class="pl-4 pb-20">
                    <p class="text-gray-500">Bill To:</p>
                    <h3 class="font-bold">Trần Thị Minh Khai</h3>
                </div>
             -->


        </div>
        <div class="flex items-end flex-col">

            <div class="pb-12">
                <h1 class=" font-normal text-4xl pb-1">PHIẾU GỬI</h1>
                <h1 class=" font-normal text-3xl pb-1">BILL OF CONSIGNMENT</h1>
                <p class="text-right text-gray-500 text-xl"># 121234</p>
            </div>

            <div class="flex">
                <div class="flex flex-col items-end">
                    <div class="w-60 pl-4 pb-7">
                        <br>
                        <h3 class="font-bold">Họ tên địa chỉ người nhận:</h3>
                        <p>Số nhà 31, ngách 63/1, Dịch Vọng Hậu, Cầu Giấy, Hà Nội</p>
                    </div>
                    <div class="w-60 pl-4 pb-7">
                        <h3 class="font-bold">Số điện thoại người nhận:</h3>
                        <p>0987654321</p>
                    </div>
                    <div class="w-60 pl-4 pb-6">
                        <h3 class="font-bold">Bưu Cục nhận:</h3>
                        <p>Quận 7 - Sài Gòn</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--Items List-->
    <div class="table w-full">
        <div class=" table-header-group bg-gray-700 text-white">
            <div class=" table-row ">
                <div class=" table-cell w-10/12 text-left py-2 px-4 rounded-l-lg border-x-[1px]">Nội dung hàng hóa
                </div>
                <div class=" table-cell w-2/12 text-center rounded-r-lg border-x-[1px]">Số lượng</div>
            </div>
        </div>

        <div class="table-row-group">
            <div class="table-row">
                <div class=" table-cell w-10/12 text-left font-bold py-1 px-4 border">Bản giới hạn violet evergarden
                    vol 3</div>
                <div class=" table-cell w-2/12 text-center border">2</div>
            </div>
        </div>
    </div>
    <!--Notes and Other info-->
    <div class="flex items-start justify-center pt-20">
        <div class="flex-1">
            <div class="w-60 pl-4 pb-7">
                <h3 class="font-bold">Cước:</h3>
                <p>Trọng lượng: 236.00(kg)</p>
                <p>Tổng cước: 15000(VNĐ)</p>
                <p>Thanh toán cước: Người gửi</p>
                <p>Số tiền phải thu: 15000(VNĐ)</p>
            </div>


        </div>
        <div class="flex items-end flex-col">
            <div class="flex">
                <div class="flex flex-col items-end">
                    <h1 class="font-bold w-60 pl-4 pb-7 mx-auto">QR Code</h1>
                    <div id="qrcode" class=""></div>
                </div>
            </div>
        </div>
    </div>

    <div class="flex items-start justify-center pt-20">
        <div class="flex-1">
            <div class="w-60 pl-4 pb-7">
                <h3 class="font-bold">Ngày, giờ gửi:</h3>
                <p>undefined</p>
            </div>
            <div class="w-60 pl-4 pb-7">
                <h3 class="font-bold">Họ tên, chữ ký người gửi:</h3>
                <br>
                <br>
                <br>
                <br>
            </div>


        </div>
        <div class="flex items-end flex-col">
            <div class="flex">
                <div class="flex flex-col items-end">
                    <div class="w-60 pl-4 pb-7  ">
                        <br>
                        <h3 class="font-bold">Ngày giờ nhận:</h3>
                        <p>27/12/2023 16:01:34</p>
                    </div>
                    <div class="w-60 pl-4 pb-7">
                        <h3 class="font-bold">Họ tên, chữ kí người nhận:</h3>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="py-6">
        <p class="text-gray-400 pb-2">Notes:</p>
        <p>Hàng dễ vỡ, xin nhẹ tay</p>
    </div>

    <div class="">
        <p class="text-gray-400 pb-2">Terms:</p>
        <p>Đồng ý với điều khoản dịch vụ</p>
    </div>
</div>`; // Đặt đoạn HTML của bạn vào đây


    // Sử dụng Renderer2 để tạo và thêm phần tử HTML vào DOM
    const div = this.renderer.createElement('div');
    this.renderer.setProperty(div, 'innerHTML', htmlContent);
    this.renderer.appendChild(this.contentContainer.nativeElement, div);

    // Lấy phần tử có id 'qrcode' sau khi nội dung đã được thêm vào DOM
    const qrCodeDiv = this.contentContainer.nativeElement.querySelector('#qrcode');

    // Tạo QR code bằng cách gọi API và chèn vào div có id 'qrcode'
    const qrCodeContent = 'Your QR Code Content'; // Replace with your actual content
    const apiUrl = `https://api.qrserver.com/v1/create-qr-code/?size=128x128&data=${qrCodeContent}`;
    qrCodeDiv.innerHTML = `<img src="${apiUrl}" alt="QR Code" style="margin-right:130px">`;
    
  }
}

