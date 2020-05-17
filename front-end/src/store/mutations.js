/**
 * mutations 만 Vuex 스토어에 저장된 상태를 변경할 수 있다.
 */
export default {
  updateMyData (state, data) {
    state.user.name = data.user.name
    state.teams = data.teams
    state.boards = data.boards
  },
  addTeam (state, team) {
    state.teams.push(team)
  },
  addBoard (state, board) {
    state.boards.push(board)
  }
}
