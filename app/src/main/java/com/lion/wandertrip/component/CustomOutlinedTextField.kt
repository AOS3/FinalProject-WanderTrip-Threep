package com.lion.a02_boardcloneproject.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomOutlinedTextField(
    // 입력값에 대한 상태관리 변수
    textFieldValue: MutableState<String> = mutableStateOf(""),
    // hint
    label:String = "",
    // 포커스가 주여지고 입력된 내용이 없을 경우 보여줄 안내 문구
    placeHolder:String = "",
    // 입력 제한을 주기위한 정규식
    inputCondition:String? = null,
    // 입력 요소 앞의 아이콘
    leadingIcon: ImageVector? = null,
    // 우측 끝의 아이콘
    trailingIconMode: LikeLionOutlinedTextFieldEndIconMode = LikeLionOutlinedTextFieldEndIconMode.NONE,
    // 한줄 입력 여부
    singleLine:Boolean = false,
    // 상단 여백
    paddingTop:Dp = 0.dp,
    // 입력 모드
    inputType:LikeLionOutlinedTextFieldInputType = LikeLionOutlinedTextFieldInputType.TEXT,
    // 입력 가능 여부
    readOnly:Boolean = false,
    // 포커싱 관리
    focusRequest:MutableState<FocusRequester>? = null,
    // 입력 요소 하단에 나오는 메세지
    supportText:MutableState<String>? = null,
    // 에러 표시
    isError:MutableState<Boolean> = mutableStateOf(false),
    // 만약 입력에 대한 검사를 체크하는 기능이 필요하다면
    isCheckValue:MutableState<Boolean>? = null,
    // 텍스트 필드 너비
    width: Dp? = null, // 너비를 입력받는 파라미터
    minLines: Int = 1,
) {

    // 비밀번호가 보이는지...
    var isShowingPasswordFlag by rememberSaveable {
        mutableStateOf(false)
    }

    // Modify 생성
    var modifier = Modifier.padding(top = paddingTop)

    // 너비가 지정되었으면, 지정된 너비로 설정하고, 아니면 fillMaxWidth로 설정
    modifier = if (width != null) {
        modifier.width(width) // width가 있으면 지정된 너비로 설정
    } else {
        modifier.fillMaxWidth() // 없으면 fillMaxWidth로 설정
    }

    if(focusRequest != null){
        modifier = modifier.focusRequester(focusRequest.value)
    }


    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue.value,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeHolder)
        },
        onValueChange = {
            if(inputCondition == null) {
                textFieldValue.value = it
            } else {
                textFieldValue.value = it.replace(inputCondition.toRegex(), "")
            }

            if(isCheckValue != null){
                isCheckValue.value = false
            }
        },
        leadingIcon = if(leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null
                )
            }
        } else {
            null
        },
        trailingIcon = when(trailingIconMode){
            LikeLionOutlinedTextFieldEndIconMode.NONE -> null
            LikeLionOutlinedTextFieldEndIconMode.TEXT -> {
                {
                    if(textFieldValue.value.isNotEmpty()){
                        IconButton(
                            onClick = {
                                textFieldValue.value = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = null
                            )
                        }
                    } else {
                        null
                    }
                }
            }
            LikeLionOutlinedTextFieldEndIconMode.PASSWORD -> {
                {
                    IconButton(
                        onClick = {
                            isShowingPasswordFlag = !isShowingPasswordFlag
                        }
                    ) {
                        if(isShowingPasswordFlag){
                            Icon(Icons.Filled.Visibility, contentDescription = null)
                        } else {
                            Icon(Icons.Filled.VisibilityOff, contentDescription = null)
                        }
                    }
                }
            }
        },
        singleLine = singleLine,
        visualTransformation =  if(isShowingPasswordFlag == false && inputType == LikeLionOutlinedTextFieldInputType.PASSWORD){
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        readOnly = readOnly,
        supportingText = if(supportText != null){
            {
                Text(text = supportText.value)
            }
        } else {
            null
        },
        isError = isError.value,
        minLines = minLines
    )
}

enum class LikeLionOutlinedTextFieldEndIconMode{
    NONE,
    TEXT,
    PASSWORD,
}

enum class LikeLionOutlinedTextFieldInputType{
    TEXT,
    PASSWORD,
    NUMBER,
}
