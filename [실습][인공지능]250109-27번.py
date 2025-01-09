import streamlit as st
import pandas as pd

# 제목 설정
st.title('고객 이탈 예측')

# 데이터 로드
st.subheader('데이터셋을 업로드 하세요')
uploaded_file = st.file_uploader('CSV 파일 업로드', type=['csv'])

if uploaded_file is not None:
    data = pd.read_csv(uploaded_file)
    st.write(data.head())
