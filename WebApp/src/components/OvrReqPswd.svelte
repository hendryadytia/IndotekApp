<script>
     import {apiurl} from '../helper/apiurl.js'
    import Card from './Card.svelte'
    import Input from '../components/inputText.svelte'
    import ButtonSmall from './ButtonSmall.svelte';
    import X from 'jquery'
    import NotificationChanges from '../components/NotificationChanges.svelte'

    import { onMount } from 'svelte'

    export let visibility = false
    export let konfirmasi = false
    export let context
    let pswd
    let triggernotif,bodynotif,headnotif,responsestatus

    function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }
    const kirim = ()=>{
       
             X.post(apiurl+"login",
                {
                    username:getCookie("username"),
                    password:pswd,
                },
                function(data, status){
                    if(status == "success")
                    {
                        if(data.code == '200'){
                            konfirmasi = true
                            visibility = false
                            pswd = ''
                        }else if(data.code == '404'){
                            triggernotif = "show"
                        bodynotif = data.message
                        headnotif = "Login"
                    }
                }
                pswd = ''
                });
        

       

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
                <h1 style="margin-bottom: auto; color: var(--accent-color)">Konfirmasi Password</h1>
            </Card>
              
            <p style="margin-top: 35px"> Mohon konfirmasi bahwa ini betul adalah anda</p>
            <Input bind:value={pswd} type="password" id="password-for-delete" placeholder="Ketikkan Password Administrasi" noLabel width='100%'/>
            <ButtonSmall on:click={kirim}> Kirim </ButtonSmall>
        </div>
    </Card>
    </div>
{/if}
{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}