import com.cristhianbonilla.oraculo.data.GptDatasource
import com.cristhianbonilla.oraculo.domain.GptRequest
import com.cristhianbonilla.oraculo.domain.GptRepositoryImpl
import com.cristhianbonilla.oraculo.util.ResultDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
class GptRepositoryImplTest {

    @Test
    fun `getDreamMeaningFromGpt returns success when datasource returns valid data`() = runBlockingTest {
        // Arrange
        val mockDataSource = mock<GptDatasource>().apply {
            stub {
                onBlocking { getDreamMeaning(any()) } doReturn MockData.dreamMeaningEntity
            }
        }

        val repository = GptRepositoryImpl(mockDataSource)
        val gptRequest = GptRequest(...)

        // Act
        val result = repository.getDreamMeaningFromGpt(gptRequest)

        // Assert
        Assert.assertTrue(result is ResultDomain.Success)
        Assert.assertEquals(MockData.dreamMeaningModel, (result as ResultDomain.Success).data)
    }

// Other tests go here...
