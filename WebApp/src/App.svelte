<script>
    import { Router, Route} from "svelte-navigator";

   
    import Navbar from "./components/Navbar.svelte"
    import Login from "./pages/Login.svelte"
    import Dashboard from './pages/Dashboard.svelte'
    import TambahMesin from './pages/TambahMesin.svelte'
    import DaftarMesin from './pages/DaftarMesin.svelte'
    import DaftarTeknisi from './pages/DaftarTeknisi.svelte'
    import DaftarPelanggan from './pages/DaftarPelanggan.svelte'
    import Settings from './pages/Settings.svelte'
    


</script>
<Router>

    <main style="display: flex;">
        <Navbar/>
        <Route component={Dashboard}/>
        <Route path="tambahmesin" component={TambahMesin}/>
        <Route path="daftarmesin" component={DaftarMesin}/>
        <Route path="daftarteknisi" component={DaftarTeknisi}/>
        <Route path="daftarpelanggan" component={DaftarPelanggan}/>
        <Route path="settings" component={Settings}/>
        <Route path="login" component={Login}/>
        
         
         
    </main>
</Router>


