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
    expect(fieldUsername.exists())
      .toBe(true)
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
   */
  it('should contain data model with initial values', () => {
    expect(wrapper.vm.form.username)
      .toEqual('')
    expect(wrapper.vm.form.emailAddress)
      .toEqual('')
    expect(wrapper.vm.form.password)
      .toEqual('')
  })

  /**
   * 데이터 모델과 폼 입력 필드 간의 바인딩 테스트
   */
  it('should have form inputs bound with data model', () => {
    const username = 'sunny'
    const emailAddress = 'sunny@taskagile.com'
    const password = 'VueJsRocks!'

    fieldUsername.setValue(username)
    fieldEmailAddress.setValue(emailAddress)
    fieldPassword.setValue(password)

    expect(wrapper.vm.form.username)
      .toEqual(username)
    expect(wrapper.vm.form.emailAddress)
      .toEqual(emailAddress)
    expect(wrapper.vm.form.password)
      .toEqual(password)
  })

  /**
   * 폼 이벤트 핸들러의 존재 여부 테스트
   */
  it('should have form submit event handler `submitForm`', () => {
    const stub = jest.fn()
    wrapper.setMethods({submitForm: stub})
    buttonSubmit.trigger('submit')

    expect(stub)
      .toBeCalled()
  })
})
