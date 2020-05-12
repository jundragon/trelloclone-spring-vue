import { mount } from '@vue/test-utils'
import RegisterPage from '@/views/RegisterPage'

describe('RegisterPage.vue', () => {

  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldPassword
  let buttonSubmit

  beforeEach(() => {
    wrapper = mount(RegisterPage)
    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
  })

  /**
   * form 요소 검증 (화면 UI)
   */
  it('should render registration form', () => {
    expect(wrapper.find('.logo').attributes().src)
      .toEqual('/images/logo.png')
    expect(wrapper.find('.tagline').text())
      .toEqual('Open source task management tool')
    expect(fieldUsername.element.value)
      .toEqual('')
    expect(fieldEmailAddress.element.value)
      .toEqual('')
    expect(fieldPassword.element.value)
      .toEqual('')
    expect(buttonSubmit.text())
      .toEqual('Create account')
  })

  /**
   * 데이터 모델 초깃값 테스트
   * 데이터 모데로가 폼 입력 필드간의 바인딩 테스트
   * 폼 이벤트 핸들러의 존재 여부 테스트
   */

})
