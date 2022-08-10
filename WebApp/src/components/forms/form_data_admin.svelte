<script>
     import {apiurl} from '../../helper/apiurl.js'

    import Card from "../Card.svelte"
    import ButtonSmall from "../ButtonSmall.svelte"
    import InputText from "../inputText.svelte"
    import NotificationChanges from "../NotificationChanges.svelte"
    import X from 'jquery'
    export let formID
    export let formCount
    export let purpose = 'edit'
    let nama,username,password,tglgabung
    let triggernotif,bodynotif,headnotif,responsestatus
    export let buttonTambah = () => {
        if(nama != "" && username!= "" && password!= ""  ){
            X.ajax({
                url:apiurl+"registeradmin",
                type:"POST",
                data: {
                    username:username,
                    password:password,
                    nama:nama
                },
                
                beforeSend: function (request) {
                                request.setRequestHeader('Authorization', getCookie('token'));
                            },
                success: function(result,status){
                    if(status == "success")
                        {
                           if(result.code != 777){
                            triggernotif = "show"
                            bodynotif = "Berhasil menambahkan Admin"
                            headnotif = "Tambah Admin"
                            nama = username = password = tglgabung = ''
                            responsestatus= "sukses"
                           }else{
                            triggernotif = "show"
                            bodynotif = "Username telah digunakan"
                            headnotif = "Tambah Admin"
                            responsestatus= "gagal"
                           }
                        }

                }
                            
                },
            )
        }else{
            triggernotif = "show"
            headnotif = "Mohon lengkapi data formulir"
        }



    
    }

       
    export let withRemoveButton = true
    let pemilikSamaClass = "sama"
    export let removeMe = ()  => {
        formCount--
        // nodeRef.parentNode.removeChild(nodeRef);
    }

    if(purpose === 'edit'){
        X.ajax({
            url:apiurl+"daftaradmin",
            type:"GET",
            data: {
                id: formID
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                nama = result.data.nama
                username = result.data.username
            }
                        
            },
            )
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
        
        buttonTambah = () => {

            X.ajax({
                url:apiurl+"editadmin",
                type:"POST",
                data: {
                    id:formID,
                    username:username,
                    password:password,
                    nama:nama,
                },
                
                beforeSend: function (request) {
                                request.setRequestHeader('Authorization', getCookie('token'));
                            },
                success: function(result){
                    triggernotif = "show"
                    bodynotif = data.message
                    headnotif = "Tambah Admin"
                    responsestatus = "sukses"

                    nama=username=password=""
                }
                            
                },
            )
        }

    }

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

    // onMount(()=>{
                
    //             buttonTambah = 
                
    // })




</script>



<div id="form_{formID}" class="dataGroup {pemilikSamaClass}" style="display: flex; flex-direction:column; gap:20px;position: relative;">
    {#if withRemoveButton}
    <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-top: -50px; position: absolute; right: 0" on:click={removeMe}/>
    {/if}
    <!-- data teknisi -->
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Admin</p>
        </div>
        <form name="datateknisi">
            <InputText style="margin-top:16px" label="Nama" name="nama" bind:value={nama}/>
            <InputText style="margin-top:16px" label="Username" name="username"  bind:value={username}/>
            <InputText style="margin-top:16px" label="Password" name="password" type="password" bind:value={password}/>
    
           
            <ButtonSmall style="width: 100%; margin-top: 10px" on:click={buttonTambah}>
                <p style="font-size: 20px;
                    color: white; font-weight: normal">
                    {#if purpose === 'tambah'}
                    Tambahkan
                    {:else if purpose === 'edit'}
                    Simpan
                    {/if}
                </p> 
            </ButtonSmall>
        </form>
    </Card>
</div>

{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}