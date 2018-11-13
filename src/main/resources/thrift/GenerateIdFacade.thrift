namespace java com.service.platform.generateId.facade

enum RequestType{
    Order,
    Disorder
}

struct RegisterIdCenterRequest {
 1: string header,
 2: i32 length
}

struct GenerateIdRequest {
 1: RequestType type,
 2: string header
}

service GenerateIdFacadeService {

    string registerIdCenter(1:RegisterIdCenterRequest request)

    string generateId(1:GenerateIdRequest request)

}



