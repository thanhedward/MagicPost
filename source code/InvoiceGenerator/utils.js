function verifyBody(data){
    if(!data?.deliveryCode){
        return {
            success: false,
            msg: "deliveryCode is missing"
        }
    }
    if(!data?.startAddress){
        return {
            success: false,
            msg: "start address is missing"
        }
    }

    if(!data?.endAddress){
        return {
            success: false,
            msg: "end address is missing"
        }
    }

    if(!data?.parcelName){
        return {
            success: false,
            msg: "parcel Name is missing"
        }
    }

    if(!data?.weight){
        return {
            success: false,
            msg: "weight is missing"
        }
    }

    if(!data?.linkTracking){
        return {
            success: false,
            msg: "link tracking to create qr code is missing"
        }
    }

    if(!data?.date){
        return {
            success: false,
            msg: "date is missing"
        }
    }

    return {
        success: true
    }
}

module.exports = {
    verifyBody
}