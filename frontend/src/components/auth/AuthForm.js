import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import palette from '../../lib/styles/palette';
import Button from '../common/Button';
import GoogleButton from './GoogleButton';
import axios from 'axios';

const client = axios.create();
/**
 * 회원가입 또는 로그인 폼을 보여줍니다.
 */

const AuthFormBlock = styled.div`
  h3 {
    margin: 0;
    color: ${palette.gray[8]};
    margin-bottom: 1rem;
  }
`;

/**
 * 스타일링된 input
 */
const StyledInput = styled.input`
  font-size: 1rem;
  border: none;
  border-bottom: 1px solid ${palette.gray[5]};
  padding-bottom: 0.5rem;
  outline: none;
  width: 100%;
  &:focus {
    color: $oc-teal-7;
    border-bottom: 1px solid ${palette.gray[7]};
  }
  & + & {
    margin-top: 1rem;
  }
`;

/**
 * 폼 하단에 로그인 혹은 회원가입 링크를 보여줌
 */
const Footer = styled.div`
  margin-top: 2rem;
  text-align: right;
  a {
    color: ${palette.gray[6]};
    text-decoration: underline;
    &:hover {
      color: ${palette.gray[9]};
    }
  }
`;

const ButtonWithMarginTop = styled(Button)`
  margin-top: 1rem;
`;

const textMap = {
  login: '로그인',
  register: '회원가입',
};

/**
 * 에러를 보여줍니다
 */
const ErrorMessage = styled.div`
  color: red;
  text-align: center;
  font-size: 0.875rem;
  margin-top: 1rem;
`;

const AuthForm = ({ type, form, onChange, onSubmit, error }) => {
  const text = textMap[type];
  const API_KEY = process.env.REACT_APP_REST_API;
  const REDIRECT_URI = 'http://localhost:3000/login';
  const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
  const code = new URL(window.location.href).searchParams.get('code');
  (({ code }) => client.get(`/api/oauth/token?code=${code}`))();

  return (
    <AuthFormBlock>
      <h3>카카오 {text}</h3>
      <a href={KAKAO_AUTH_URI}>카카오로 시작하기</a>

      {/* <h3>구글 {text}</h3> */}
      {/* <GoogleButton /> */}
    </AuthFormBlock>
  );
};

export default AuthForm;
