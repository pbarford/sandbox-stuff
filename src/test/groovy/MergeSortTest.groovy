import com.pjb.test.MergeSort
import spock.lang.Specification


class MergeSortTest extends Specification {
    def "test sorting using MergeSort" () {
         given:
            def numbers = [5,9,2,4,8,9,1,3] as Integer[]
            def merge = new MergeSort();
         when:
            def result = merge.sort(numbers)
        then:
            result == [1,2,3,4,5,8,9,9]

    }
}
