import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

/**
 *  dependencies
 *      implementation "com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0"
 *      implementation "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.0"
 */

class JacksonOptionalDataParsing {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val dataEmptySpecialStr = """
                <TABLES>
                    <TABLE NAME="abcd" TIME="2013.05.27 00:00:00" >
                        <SPECIAL></SPECIAL> 
                    </TABLE>
                </TABLES>
            """.trimIndent()
            val xmlMapper = XmlMapper()

            println(xmlMapper.readValue(dataEmptySpecialStr, TABLES::class.java))
            // Output
            //TABLES(table=TABLE(special=SPECIAL(week=WEEK(param=128, name=abcde)
            // , day=DAY(date=16714778, mask=128)), name=abcd, time=2013.05.27 00:00:00))

            val dataStr = """
                <TABLES>
                    <TABLE NAME="abcd" TIME="2013.05.27 00:00:00" >
                        <SPECIAL>
                            <WEEK NAME="abcde" PARAM="128" />
                        </SPECIAL> 
                    </TABLE>
                </TABLES>
            """.trimIndent()

            println(xmlMapper.readValue(dataStr, TABLES::class.java))
            // Output
            // TABLES(table=TABLE(special=null, name=abcd, time=2013.05.27 00:00:00))
        }
    }
}

data class TABLES(@JacksonXmlProperty(localName = "TABLE") val table: TABLE)

data class TABLE(
    @JacksonXmlProperty(localName = "SPECIAL") val special: SPECIAL?,
    @JacksonXmlProperty(localName = "NAME") val name: String,
    @JacksonXmlProperty(localName = "TIME") val time: String
)

data class SPECIAL(
    @JacksonXmlProperty(localName = "WEEK") var week: WEEK?,
    @JacksonXmlProperty(localName = "DAY") var day: DAY?
)

data class DAY(
    @JacksonXmlProperty(localName = "DATE") val date: String,
    @JacksonXmlProperty(localName = "MASK") val mask: String
)

data class WEEK(
    @JacksonXmlProperty(localName = "PARAM") val param: String,
    @JacksonXmlProperty(localName = "NAME") val name: String
)