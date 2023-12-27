const HTMLToPDF = require('convert-html-to-pdf').default;

function getDeliveryItemsHTML(items){
    let data = ""
    for(let item of items){
        data += `
    <div class="table-row">
        <div class=" table-cell w-6/12 text-left font-bold py-1 px-4">${item.name}</div>
        <div class=" table-cell w-[10%] text-center">${item.qty}</div>
        <div class=" table-cell w-2/12 text-center">₹${item.rate}</div>
        <div class=" table-cell w-2/12 text-center">₹${item.amount}</div>
    </div>
    `
    }
    return data
}

function getDeliveryHTML(options){
    return `
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>
        <title>Invoice</title>
        <style>
            /*! tailwindcss v3.0.12 | MIT License | https://tailwindcss.com*/*,:after,:before{box-sizing:border-box;border:0 solid #e5e7eb}:after,:before{--tw-content:""}html{line-height:1.5;-webkit-text-size-adjust:100%;-moz-tab-size:4;-o-tab-size:4;tab-size:4;font-family:ui-sans-serif,system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Helvetica Neue,Arial,Noto Sans,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol,Noto Color Emoji}body{margin:0;line-height:inherit}hr{height:0;color:inherit;border-top-width:1px}abbr:where([title]){-webkit-text-decoration:underline dotted;text-decoration:underline dotted}h1,h2,h3,h4,h5,h6{font-size:inherit;font-weight:inherit}a{color:inherit;text-decoration:inherit}b,strong{font-weight:bolder}code,kbd,pre,samp{font-family:ui-monospace,SFMono-Regular,Menlo,Monaco,Consolas,Liberation Mono,Courier New,monospace;font-size:1em}small{font-size:80%}sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:initial}sub{bottom:-.25em}sup{top:-.5em}table{text-indent:0;border-color:inherit;border-collapse:collapse}button,input,optgroup,select,textarea{font-family:inherit;font-size:100%;line-height:inherit;color:inherit;margin:0;padding:0}button,select{text-transform:none}[type=button],[type=reset],[type=submit],button{-webkit-appearance:button;background-color:initial;background-image:none}:-moz-focusring{outline:auto}:-moz-ui-invalid{box-shadow:none}progress{vertical-align:initial}::-webkit-inner-spin-button,::-webkit-outer-spin-button{height:auto}[type=search]{-webkit-appearance:textfield;outline-offset:-2px}::-webkit-search-decoration{-webkit-appearance:none}::-webkit-file-upload-button{-webkit-appearance:button;font:inherit}summary{display:list-item}blockquote,dd,dl,figure,h1,h2,h3,h4,h5,h6,hr,p,pre{margin:0}fieldset{margin:0}fieldset,legend{padding:0}menu,ol,ul{list-style:none;margin:0;padding:0}textarea{resize:vertical}input::-moz-placeholder,textarea::-moz-placeholder{opacity:1;color:#9ca3af}input:-ms-input-placeholder,textarea:-ms-input-placeholder{opacity:1;color:#9ca3af}input::placeholder,textarea::placeholder{opacity:1;color:#9ca3af}[role=button],button{cursor:pointer}:disabled{cursor:default}audio,canvas,embed,iframe,img,object,svg,video{display:block;vertical-align:middle}img,video{max-width:100%;height:auto}[hidden]{display:none}*,:after,:before{--tw-translate-x:0;--tw-translate-y:0;--tw-rotate:0;--tw-skew-x:0;--tw-skew-y:0;--tw-scale-x:1;--tw-scale-y:1;--tw-pan-x: ;--tw-pan-y: ;--tw-pinch-zoom: ;--tw-scroll-snap-strictness:proximity;--tw-ordinal: ;--tw-slashed-zero: ;--tw-numeric-figure: ;--tw-numeric-spacing: ;--tw-numeric-fraction: ;--tw-ring-inset: ;--tw-ring-offset-width:0px;--tw-ring-offset-color:#fff;--tw-ring-color:#3b82f680;--tw-ring-offset-shadow:0 0 #0000;--tw-ring-shadow:0 0 #0000;--tw-shadow:0 0 #0000;--tw-shadow-colored:0 0 #0000;--tw-blur: ;--tw-brightness: ;--tw-contrast: ;--tw-grayscale: ;--tw-hue-rotate: ;--tw-invert: ;--tw-saturate: ;--tw-sepia: ;--tw-drop-shadow: ;--tw-backdrop-blur: ;--tw-backdrop-brightness: ;--tw-backdrop-contrast: ;--tw-backdrop-grayscale: ;--tw-backdrop-hue-rotate: ;--tw-backdrop-invert: ;--tw-backdrop-opacity: ;--tw-backdrop-saturate: ;--tw-backdrop-sepia: }.flex{display:flex}.table{display:table}.table-cell{display:table-cell}.table-header-group{display:table-header-group}.table-row-group{display:table-row-group}.table-row{display:table-row}.hidden{display:none}.w-60{width:15rem}.w-40{width:10rem}.w-full{width:100%}.w-\[12rem\]{width:12rem}.w-9\/12{width:75%}.w-3\/12{width:25%}.w-6\/12{width:50%}.w-2\/12{width:16.666667%}.w-\[10\%\]{width:10%}.flex-1{flex:1 1 0%}.flex-col{flex-direction:column}.items-start{align-items:flex-start}.items-end{align-items:flex-end}.justify-center{justify-content:center}.rounded-l-lg{border-top-left-radius:.5rem;border-bottom-left-radius:.5rem}.rounded-r-lg{border-top-right-radius:.5rem;border-bottom-right-radius:.5rem}.border-x-\[1px\]{border-left-width:1px;border-right-width:1px}.bg-gray-700{--tw-bg-opacity:1;background-color:rgb(55 65 81/var(--tw-bg-opacity))}.p-10{padding:2.5rem}.py-1{padding-top:.25rem;padding-bottom:.25rem}.py-2{padding-top:.5rem;padding-bottom:.5rem}.px-4{padding-left:1rem;padding-right:1rem}.py-6{padding-top:1.5rem;padding-bottom:1.5rem}.pl-4{padding-left:1rem}.pb-20{padding-bottom:5rem}.pb-16{padding-bottom:4rem}.pb-1{padding-bottom:.25rem}.pb-2{padding-bottom:.5rem}.pt-20{padding-top:5rem}.pr-10{padding-right:2.5rem}.pl-24{padding-left:6rem}.pb-6{padding-bottom:1.5rem}.pl-10{padding-left:2.5rem}.text-left{text-align:left}.text-center{text-align:center}.text-right{text-align:right}.text-4xl{font-size:2.25rem;line-height:2.5rem}.text-xl{font-size:1.25rem;line-height:1.75rem}.font-bold{font-weight:700}.font-normal{font-weight:400}.text-gray-500{--tw-text-opacity:1;color:rgb(107 114 128/var(--tw-text-opacity))}.text-white{--tw-text-opacity:1;color:rgb(255 255 255/var(--tw-text-opacity))}.text-gray-400{--tw-text-opacity:1;color:rgb(156 163 175/var(--tw-text-opacity))}.text-black{--tw-text-opacity:1;color:rgb(0 0 0/var(--tw-text-opacity))}
            body {
                text-align: center;
                padding: 50px;
              }
          
              #qrcode {
                max-width: 200px;
                margin: auto;
              }
        </style>
    </head>
    <body>
        <div class="p-10">
            <!--Logo and Other info-->
            <div class="flex items-start justify-center">
                <div class="flex-1">
                    <div class="w-60 pb-6">
                        <img class="w-40" src="https://upload.wikimedia.org/wikipedia/en/7/78/Post_Office_Logo.svg" alt="Logo">
                    </div>
                    
                    <div class="w-60 pl-4 pb-7">
                        <h3 class="font-bold">Họ tên địa chỉ người gửi:</h3>
                        <p>${options.startAddress}</p>
                    </div>
                    <div class="w-60 pl-4 pb-7">
                        <h3 class="font-bold">Số điện thoại người gửi:</h3>
                        <p>0987654321</p>
                    </div>
                    <div class="w-60 pl-4 pb-6">
                        <h3 class="font-bold">Bưu Cục gửi:</h3>
                        <p>${options.postStart}</p>
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
                        <p class="text-right text-gray-500 text-xl"># ${options.deliveryCode}</p>
                    </div>
    
                    <div class="flex">
                        <div class="flex flex-col items-end">
                            <div class="w-60 pl-4 pb-7">
                                <br>
                                <h3 class="font-bold">Họ tên địa chỉ người nhận:</h3>
                                <p>${options.endAddress}</p>
                            </div>
                            <div class="w-60 pl-4 pb-7">
                                <h3 class="font-bold">Số điện thoại người nhận:</h3>
                                <p>0987654321</p>
                            </div>
                            <div class="w-60 pl-4 pb-6">
                                <h3 class="font-bold">Bưu Cục nhận:</h3>
                                <p>${options.postEnd}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!--Items List-->
            <div class="table w-full">
                <div class=" table-header-group bg-gray-700 text-white">
                    <div class=" table-row ">
                        <div class=" table-cell w-10/12 text-left py-2 px-4 rounded-l-lg border-x-[1px]">Nội dung hàng hóa</div>
                        <div class=" table-cell w-2/12 text-center rounded-r-lg border-x-[1px]">Số lượng</div>
                    </div>
                </div>
    
                <div class="table-row-group">
                    <div class="table-row">
                        <div class=" table-cell w-10/12 text-left font-bold py-1 px-4 border">${options.parcelName}</div>
                        <div class=" table-cell w-2/12 text-center border">2</div>
                    </div>
                </div>
            </div>
            <!--Notes and Other info-->
            <div class="flex items-start justify-center pt-20">
                <div class="flex-1">
                    <div class="w-60 pl-4 pb-7">
                        <h3 class="font-bold">Cước:</h3>
                        <p>Trọng lượng: ${options.weight}(kg)</p>
                        <p>Tổng cước: 15000(VNĐ)</p>
                        <p>Thanh toán cước: Người gửi</p>
                        <p>Số tiền phải thu: 15000(VNĐ)</p>
                    </div>
    
                    
                </div>
                <div class="flex items-end flex-col">
                    <div class="flex">
                        <div class="flex flex-col items-end">
                            <h1 class="font-bold w-60 pl-4 pb-7">QR Code</h1>
                            <div id="qrcode"><br></div>
                        </div>
                    </div>
                </div>
            </div>
    
            <div class="flex items-start justify-center pt-20">
                <div class="flex-1">
                    <div class="w-60 pl-4 pb-7">
                        <h3 class="font-bold">Ngày, giờ gửi:</h3>
                        <p>${options.createDate}</p>
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
        </div>
        
        <script>
            // Sử dụng thư viện qrcode.js để tạo QR code
            var qrcode = new QRCode(document.getElementById("qrcode"), {
            text: "${options.linkTracking}",
            width: 128,
            height: 128
            });
        </script>
    </body>
    </html>
    `
}

async function getInvoice(options) {
    return new Promise(async (resolve,reject) => {
        try {
            const html = getDeliveryHTML(options)
            const htmlToPDF = new HTMLToPDF(html)

            const pdf = await htmlToPDF.convert({waitForNetworkIdle: true, browserOptions: {defaultViewport: {width: 1920, height: 1080}}, pdfOptions: {height: 1200, width:900, timeout: 0}})
            resolve(pdf)
        } catch(err){
            reject(err)
        }
    })
}

module.exports = {
    getInvoice, getDeliveryHTML
}