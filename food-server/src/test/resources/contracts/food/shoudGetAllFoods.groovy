package food

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/foods'
    }
    response {
        status 200
//        body([[id: 1L, name: "Rice", descriptions: "White Rice"], [id: 2L, name: "Fried Rice", descriptions: "Premium Rice"]])
        body("""
[
    {
        "id": 1,
        "name": "Rice",
        "description": "White Rice"
    },
    {
        "id": 2,
        "name": "Fried Rice",
        "description": "Premium Rice"
    }
]
        """)
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
}
