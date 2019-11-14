package sample.Bean.HXYYBean

data class GetDeptDocRetBean(
        var data: Data?,
        var result: Boolean // true
) {
    data class Data(
            var daytype1Schs: List<Daytype1Sch>,
            var daytype2Schs: List<Daytype1Sch>,
            var daytype4Schs: List<Daytype1Sch>,
            var nowDateStr: String // 2019-09-21
    ) {
        class Daytype1Sch(
                var dayType: String, // 1
                var docId: String, // 0b1837ce2e2f4ef7b4b2baf2f712d18e
                var docName: String, // 魏珉
                var resNo: Int, // 0
                var titleShow: String, // 知名专家
                var weekType: String, // 1
                var schDate: String, // 1
                var schId: String // 1
                //var 预约科室: String
                /*
                "schDate": "2019-09-24",
                "schId": "69e69eb21d824b00bf66201229af00a8"
                */
        ) {
            override fun toString() = when (dayType) {
                "1" -> "上午"
                "2" -> "下午"
                else -> dayType
            } + "-" + docName
        }
    }
}