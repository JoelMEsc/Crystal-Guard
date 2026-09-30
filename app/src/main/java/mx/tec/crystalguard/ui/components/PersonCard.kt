package mx.tec.crystalguard.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.tec.crystalguard.domain.people.Educator
import mx.tec.crystalguard.domain.people.Student
import mx.tec.crystalguard.ui.theme.CrystalGuardTheme

@Composable
fun PersonCard(
    name: String,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    imageRes: Int? = null,
    onClick: () -> Unit = {},
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                when {
                    imageRes != null -> {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(48.dp),
                        )
                    }
                    imageVector != null -> {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = name,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(26.dp),
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = name,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(26.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun PersonCard(
    student: Student,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    PersonCard(
        name = student.name,
        modifier = modifier,
        imageVector = student.profileImage,
        imageRes = student.profileImageRes,
        onClick = onClick,
    )
}

@Composable
fun PersonCard(
    educator: Educator,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    PersonCard(
        name = educator.name,
        modifier = modifier,
        imageVector = educator.profileImage,
        imageRes = educator.profileImageRes,
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun PersonCardPreview() {
    val sampleEducator = Educator(
        id = 1,
        name = "Profra. María Elena García",
        age = 34,
        roleTitle = "Educadora Titular",
        groupIds = listOf(1),
    )

    val sampleStudent = Student(
        id = 101,
        name = "Lucas Martínez López",
        age = 5,
        groupId = 1,
    )

    CrystalGuardTheme {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Profesor/a:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(6.dp))
            PersonCard(educator = sampleEducator, onClick = {})

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Alumno/a:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(6.dp))
            PersonCard(student = sampleStudent, onClick = {})
        }
    }
}
