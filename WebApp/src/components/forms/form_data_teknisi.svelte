<script>
     import {apiurl} from '../../helper/apiurl.js'
    import Card from "../Card.svelte"
    import ButtonSmall from "../ButtonSmall.svelte"
    import InputText from "../inputText.svelte"
    import NotificationChanges from "../NotificationChanges.svelte"
    import X from 'jquery'
    export let formID
    export let formCount
    export let purpose 
    let nama,username,alamat,telepon,keahlian,tgldibuat,password
    let triggernotif,bodynotif,headnotif,responsestatus

    nama=username=alamat=telepon=keahlian=tgldibuat=password=""


    export let buttonTambah = () => {
        if(purpose === 'tambah'){
            if(nama != "" && username!= "" && alamat!= "" && telepon!= "" && keahlian!= ""  ){
                X.ajax({
                    url:apiurl+"registerteknisi",
                    type:"POST",
                    data: {
                        username:username,
                        nama:nama,
                        alamat:alamat,
                        telepon:telepon,
                        keahlian:keahlian,
                        tgl_bergabung:tgldibuat
                    },
                    
                    beforeSend: function (request) {
                                    request.setRequestHeader('Authorization', getCookie('token'));
                                },

                    success: function(result,status){
                        if(status == "success")
                                {
                                    if(result.code != 777){
                                       
                                    triggernotif = "show"
                                    responsestatus = "sukses"
                                    headnotif = "Tambah Teknisi"
                                    bodynotif = "Berhasil Menambahkan Teknisi"
                                    nama=username=alamat=telepon=keahlian=tgldibuat=password=""
                                    setTimeout(function (){
                                        removeMe()    
                                    }, 2000);
                                    }else{
                                        triggernotif = "show"
                                        bodynotif = "Username telah digunakan"
                                        headnotif = "Tambah Teknisi"
                                        responsestatus= "gagal"
                                    }
                                }        
                            }
                        }
                    )
            }else{
                triggernotif = "show"
                headnotif = "Mohon lengkapi data"
            }
           
        }else if(purpose === 'edit'){
            if(nama != "" && username!= "" && alamat!= "" && telepon!= "" && keahlian!= ""  ){
                    X.ajax({
                    url:apiurl+"teknisiedit",
                    type:"POST",
                    data: {
                        id: formID,
                        username:username,
                        nama:nama,
                        alamat:alamat,
                        no_telp:telepon,
                        keahlian:keahlian,
                        password:password,
                        tgl_bergabung:tgldibuat
                    },
                    
                    beforeSend: function (request) {
                                    request.setRequestHeader('Authorization', getCookie('token'));
                                },
                    success: function(result,status){
                        if(status == "success")
                                {
                        triggernotif = "show"
                        bodynotif = "berhasil edit"
                        headnotif = "edit Teknisi"
                        responsestatus = "sukses"

                        setTimeout(function (){
                                        removeMe()    
                                    }, 2000);
                                }

                    }
                                
                    },
                )
            }else{
                triggernotif = "show"
                headnotif = "Mohon lengkapi data formulir"
            }
        }
    }
    let pemilikSamaClass = "sama"
    export let removeMe = ()  => {
        formCount--
        // nodeRef.parentNode.removeChild(nodeRef);
    }
    if(purpose === 'edit'){
        X.ajax({
            url:apiurl+"teknisiedit",
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
               alamat = result.data.alamat
               telepon = result.data.no_telp
               keahlian = result.data.keahlian
               password = result.data.password
            }
                        
            },
        )
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
  
</script>



<div id="form_{formID}" class="dataGroup {pemilikSamaClass}" style="display: flex; flex-direction:column; gap:20px;position: relative;">
    <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-top: -50px; position: absolute; right: 0" on:click={removeMe}/>
    <!-- data teknisi -->
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Teknisi</p>
        </div>
        <form name="datateknisi">
            <InputText style="margin-top:16px" label="Nama" name="nama" bind:value={nama}/>
            <InputText style="margin-top:16px" label="Username" name="username" bind:value={username}/>
            <InputText style="margin-top:16px" label="Alamat" name="alamat" bind:value={alamat}/>
            <InputText style="margin-top:16px" label="No. Telepon" name="telepon" bind:value={telepon}/>
            {#if purpose == 'edit'}
            <InputText style="margin-top:16px" label="Password" name="password" bind:value={password}/>
                
            {/if}
            
            <InputText style="margin-top:16px" label="Keahlian" height="75" name="keahlian" bind:value={keahlian}/>
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