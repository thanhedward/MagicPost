require('dotenv').config()
const express = require("express")
const {verifyBody} = require("./utils");
const {getInvoice} = require("./invoice");
const app = express()
const fs = require('fs');
const {getDeliveryHTML} = require("./invoice")
app.use(express.json())
const PORT = process.env["PORT"] || 11000

const deliveryOptions = {
    logo: "https://thumbs.dreamstime.com/b/laundry-basket-icon-trendy-design-style-isolated-white-background-vector-simple-modern-flat-symbol-web-site-mobile-135748439.jpg",
    deliveryCode: 123,
    linkTracking: "https://www.24h.com.vn/",
    startAddress: "Số nhà 31, ngách 63/1, Dịch Vọng Hậu, Cầu Giấy, Hà Nội",
    endAddress: "Số nhà 31, ngách 63/1, Dịch Vọng Hậu, Cầu Giấy, Hà Nội",
    phone: "0123456789",
    postStart: "Cầu Giấy - Hà Nội",
    postEnd: "Quận 7 - Sài Gòn",
    parcelName: "Bản giới hạn oshi no ko vol 3",
    createDate: "Oct 2, 2022",
    paymentTerms: "Delivery Items Receipt",
    weight: "235.00",
    balanceDue: "235.00",
    notes: "Thanks for being an awesome customer!",
    terms: "This invoice is auto generated at the time of delivery. If there is any issue, Contact provider"
}

const duongDanFileDich = 'test1.pdf';

app.post("/getInvoice", (req,res) => {
    // const result = verifyBody(req.body)
    // if(result.success){
    // if(true){
    //     // getInvoice(req.body).then(pdf => {
    //     getInvoice(deliveryOptions).then(pdf => {
    //         res.status(200)
    //         res.contentType("application/pdf");
    //         res.send(pdf);
    //     }).catch(err => {
    //         console.error(err)
    //         res.status(500).send({success: false, error: "something went wrong"})
    //     })
    // } else {
    //     res.status(400).send(result)
    // }

    let saved_file_path = "./bills/"

    const result = verifyBody(req.body);

    if(result.success){
        saved_file_path = saved_file_path.concat(req.body.deliveryCode);
        saved_file_path = saved_file_path.concat(".pdf");    
        console.log(saved_file_path)
        getInvoice(req.body).then(pdf => {
            // res.contentType("application/pdf");
            // res.send(pdf);
            fs.writeFile(saved_file_path, pdf, (err) => {
                if (err) {
                console.error(err);
                return;
                }
                console.log('Hóa đơn được lưu thành công!');
                res.status(200).send({msg: "Hóa đơn được lưu thành công!"})
            });
        }).catch(err => {
            console.error(err)
            res.status(500).send({success: false, error: "something went wrong"})
        })
    } else {
        res.status(400).send(result);
    }
})

app.post("/sample", (req,res) => {
    const result = verifyBody(req.body)
    // const result = verifyBody(deliveryOptions)
    if(result.success){
        res.status(200).send(getDeliveryHTML(req.body))
    } else {
        res.status(400).send(result)
    }
})

app.get("/", (req,res)=> {
    res.status(200).send({msg: "Hi there, welcome to Invoice API. Go to /sample route to get sample data"})
})

app.listen(PORT, () => {
    console.log(`Listening on PORT ${PORT}`)
})