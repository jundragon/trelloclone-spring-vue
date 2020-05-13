import { mount } from '@vue/test-utils'
import LoginPage from '@/views/LoginPage'

describe('LoginPage.vue', () => {
  let wrapper

  beforeEach(() => {
    wrapper = mount(LoginPage)
  })

  it('should render correct contents', () => {
    expect(wrapper.find('h1').text())
      .toEqual('TrelloClone')
  })
})
