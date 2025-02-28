import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.lion.wandertrip.ui.theme.NanumSquareRoundRegular
import com.lion.wandertrip.util.AccommodationItemCat3
import com.lion.wandertrip.util.RestaurantItemCat3
import com.lion.wandertrip.util.TripItemCat2

@Composable
fun ScheduleItemCategoryChips(
    itemCode: Int,
    selectedCategoryCode: String?,
    onCategorySelected: (String?) -> Unit
) {
    val categories: List<Any> = when (itemCode) {
        12 -> TripItemCat2.values().toList()
        39 -> RestaurantItemCat3.values().toList()
        32 -> AccommodationItemCat3.values().toList()
        else -> emptyList()
    }

    LazyRow(modifier = Modifier.padding(0.dp)) {
        items(categories) { category ->
            val (categoryCode, categoryName) = when (category) {
                is TripItemCat2 -> category.catCode to category.catName
                is RestaurantItemCat3 -> category.catCode to category.catName
                is AccommodationItemCat3 -> category.catCode to category.catName
                else -> "" to ""
            }

            val isSelected = selectedCategoryCode == categoryCode

            FilterChip(
                selected = isSelected,
                onClick = {
                    onCategorySelected(if (isSelected) null else categoryCode)
                },
                label = {
                    Text(
                        text = categoryName,
                        fontFamily = NanumSquareRoundRegular,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            tint = Color.White,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF435C8F),
                    containerColor = Color.White,
                    labelColor = Color.Black,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}
