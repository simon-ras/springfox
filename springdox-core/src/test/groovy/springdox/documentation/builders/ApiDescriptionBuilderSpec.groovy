package springdox.documentation.builders
import com.google.common.collect.Ordering
import spock.lang.Specification
import springdox.documentation.service.Operation

class ApiDescriptionBuilderSpec extends Specification {
  def "Setting properties on the builder with non-null values"() {
    given:
      def orderingMock = Mock(Ordering)
      def sut = new ApiDescriptionBuilder(orderingMock)
    and:
      orderingMock.sortedCopy(value) >> value
    when:
      sut."$builderMethod"(value)
    and:
      def built = sut.build()
    then:
      built."$property" == value

    where:
      builderMethod   | value             | property
      'path'          | 'urn:some-path'   | 'path'
      'description'   | 'description'     | 'description'
      'operations'    | [Mock(Operation)] | 'operations'
      'hidden'        | true              | 'hidden'
  }

  def "Setting properties on the builder with null values preserves previous value"() {
    given:
      def orderingMock = Mock(Ordering)
      def sut = new ApiDescriptionBuilder(orderingMock)
    and:
      orderingMock.sortedCopy(value) >> value
    when:
      sut."$builderMethod"(value)
    and:
      sut."$builderMethod"(null)
    and:
      def built = sut.build()
    then:
      built."$property" == value

    where:
      builderMethod   | value             | property
      'path'          | 'urn:some-path'   | 'path'
      'description'   | 'description'     | 'description'
      'operations'    | [Mock(Operation)] | 'operations'
  }
}