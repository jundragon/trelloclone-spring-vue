import meService from '@/services/me'

/**
 * 액션만 뮤테이션을 commit 할 수 있다.
 */

export const getMyData = ({ commit }) => {
  meService.getMyData().then(data => {
    commit('updateMyData', data)
  })
}

export const addTeam = ({ commit }, team) => {
  commit('addTeam', team)
}

export const addBoard = ({ commit }, board) => {
  commit('addBoard', board)
}
