<script>
     import {apiurl} from '../../helper/apiurl.js'
    import Card from "../Card.svelte"
    import ButtonSmall from "../ButtonSmall.svelte"
    import InputText from "../inputText.svelte"
    import NotificationChanges from '../NotificationChanges.svelte'

    import X from 'jquery'

    export let formID
    export let formCount
    export let purpose
    export let buttonTambah = () => {}
    export let pemilikBeda = true
    export let withRemoveButton = true
    let pemilikSamaClass = "sama"
    let nama,alamat,notelp
    let triggernotif,bodynotif,headnotif,responsestatus

    export let removeMe = ()  => {
        formCount--
        // nodeRef.parentNode.removeChild(nodeRef);
    }

    if(purpose === 'edit'){
        X.ajax({
            url:apiurl+"daftarpelanggan",
            type:"GET",
            data: {
                id: formID
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                nama = result.data.nama
                alamat = result.data.alamat
                notelp = result.data.no_telp
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
            if(nama != "" && alamat != "" && notelp != ""){
                X.ajax({
                url:apiurl+"pelangganedit",
                type:"POST",
                data: {
                    id:formID,
                    nama:nama,
                    alamat:alamat,
                    no_telp:notelp
                },
                
                beforeSend: function (request) {
                                request.setRequestHeader('Authorization', getCookie('token'));
                            },
                success: function(result){
                    triggernotif = "show"
                    bodynotif = "Berhasil Edit Data Pelanggan"
                    headnotif = "Edit Pelanggan"
                    responsestatus = "sukses"
                    setTimeout(function (){
                    removeMe()
                    },2000)
                }
                            
                },
            )
            }else{
                triggernotif = "show"
                    bodynotif = ""
                    headnotif = "mohon lengkapi data formulir"
                    responsestatus = "xx"

            }
                
        } 
    }
   
</script>



<div id="form_{formID}" class="dataGroup {pemilikSamaClass}" style="display: flex; flex-direction:column; gap:20px;position: relative;">
    {#if withRemoveButton}
    <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-top: -50px; position: absolute; right: 0" on:click={removeMe}/>
    {/if}
    <!-- data teknisi -->
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Pelanggan</p>
        </div>
        <form name="datateknisi">
            <InputText style="margin-top:16px" label="Nama" name="nama" bind:value={nama} />
            <InputText style="margin-top:16px" label="Alamat" name="alamat" bind:value={alamat}/>
            <InputText style="margin-top:16px" label="No. Telepon" name="telepon" bind:value={notelp}/>
            
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