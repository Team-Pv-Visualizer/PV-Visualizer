customElements.define("school-dashboard", class extends HTMLElement {
    connectedCallback() {
const shadowRoot = this.attachShadow({mode: "open"});
shadowRoot.innerHTML = `
  <style>
    :host {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      position: absolute;
      right: 0;
      left: 0;
      top: 0;
      bottom: 0;
      margin: auto;
    }

    object {
      margin: 20px;
    }

  </style>
  <div style="display: flex;">
    <object data="http://localhost:3000/d-solo/wakVnfo4z/fronius-dashboard?orgId=1&refresh=5s&from=1675123200000&to=1675209599999&theme=light&panelId=2" width="650" height="300"></object>
    <object data="http://localhost:3000/d-solo/wakVnfo4z/fronius-dashboard?orgId=1&refresh=5s&from=1675111814309&to=1675111944779&panelId=4&theme=light" width="650" height="300"></object>
  </div>
  <div style="display: flex;">
    <object data="http://localhost:3000/d-solo/wakVnfo4z/fronius-dashboard?orgId=1&refresh=5s&from=1675123200000&to=1675209599999&theme=light&panelId=8" width="650" height="300"></object>
    <object data="http://localhost:3000/d-solo/wakVnfo4z/fronius-dashboard?orgId=1&refresh=5s&from=1675123200000&to=1675209599999&theme=light&panelId=6" width="650" height="300"></object>
  </div>
`;
}
  });