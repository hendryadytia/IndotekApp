<script>
    import {isLogin} from '../stores/LoginCondition'
    import Card from './Card.svelte'
    import { useNavigate, useLocation } from "svelte-navigator";

    import Input from '../components/inputText.svelte'
    import ButtonSmall from './ButtonSmall.svelte';
    import X from 'jquery'
    import { onMount } from 'svelte'

    export let visibility = false
    export let konfirmasi = false
    let pswd
    const navigate = useNavigate();
    const location = useLocation();

    const jawab = (konfirm)=>{
        if (konfirm == 'lanjut') {    
           konfirmasi = true
           visibility = false
        }else{
            visibility = false
        }
    }
    function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}
    const logoutin = () => {
        deleteAllCookies()
        navigate("/login", {
        state: { from: $location.pathname },
        replace: true,
         });
         isLogin.set("")
        //  document.cookie = "token= ; expires = Thu, 01 Jan 1970 00:00:00 GMT"
    }

    onMount(()=> {
        X('.overlay-confirm').on('click', function(e){
                if(e.target == e.currentTarget) {
                    pswd = ''
                    visibility = false
                }
            })
    })
   
</script>
{#if visibility}
  <div style="display:flex; width: 100vw; position: absolute; top:0; left:0; bottom:0; background-color: rgba(0, 0, 0, 0.363);  align-items: center; justify-content: center; z-index: 30" class="overlay-confirm">
    <Card width=300 useShadow style="position:relative">
        <div style="display: flex; flex-direction: column; justify-content:flex-end; height: inherit; justifiy-content: flex-end; gap: 20px"> 
            <Card width=300 useShadow style="margin-top: -10px;margin-left:-10px; top:0; position: absolute">
                <h1 style="margin-bottom: auto; color: var(--accent-color)">Konfirmasi Logout</h1>
            </Card>
              
            <p style="margin-top: 35px"> <slot/></p>
            <div style="display: flex; justify-content: space-between">
                <ButtonSmall on:click={()=> jawab('batal')}> Batal </ButtonSmall>
                <ButtonSmall on:click={logoutin}> Logout </ButtonSmall>
            </div>
            
        </div>
    </Card>
    </div>
{/if}
