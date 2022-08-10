<script>
     import {apiurl} from '../../helper/apiurl.js'
     import DLFC from '../DataListForCard.svelte'
    import ORK from '../OvrReqKonfirm.svelte'
    import Table from '../Table.svelte'
    import { v4 as uuidv4 } from 'uuid';
    import Card from "../Card.svelte"
    import ButtonSmall from "../ButtonSmall.svelte"
    import InputText from "../inputText.svelte"
    import X from 'jquery'
    import NotificationChanges from '../NotificationChanges.svelte'

    let triggernotif,bodynotif,headnotif,responsestatus

    let tableHead = ['id',
                            'Nama',
                            'Alamat',
                            'Proses'
                        ]
    let dtblCFG =      {
                "ajax":{
                        "url": apiurl+"daftarpelanggan",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        }
                    },
                columns: [
                        { data: 'id' },
                        { data: 'nama' },
                        { data: 'alamat' },
                        { data: 'jml_diproses' }

         
                    ],
                "columnDefs": 
                [ 
                    // field 1 isinya id, di hide
                    {
                        "targets": -1,
                        "render" : function ( data, type, row ) {
                            return "<div class='acw' style='display:flex;justify-content:space-between'>" + data + " <img src='./icons/table_menu.svg' class='cxbtn' style='height:18px; width: 20px '/> </div>"
                        }
                    },
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
                    {
                        "targets": 1,
                        "width": "25%",
                        "render" : function ( data, type, row ) {
                            return data.length > 14 ? data.substring(0,13)+"..." : data
                        },
                    } ,
                ]
            }

         
    let orkvisibility= false
    export let adaygkosong
    
    let nodeRef,pemilik
    let daridafpel,iddafpel,formpemilikdisabled,teksbtndafpel
    teksbtndafpel = "Pilih dari daftar Pelanggan"
    $:  cekformpel(daridafpel)
    function cekformpel(daridafpel){
        if(daridafpel != null){
            formpemilikdisabled = "disabled"
            teksbtndafpel = "reset"
        }else{
            teksbtndafpel = "Pilih dari daftar Pelanggan"
        }
    }

    const btnbukadafpel = () => {
        orkvisibility = true
    }

    const btnreset = () =>{
        formpemilikdisabled = ""
        iddafpel = ""
        daridafpel = null
        namaPemilik=alamat=notelp = ""
    }

    adaygkosong = true
  
    export let formID,id
    export let namaPemilik,alamat,notelp
    export let edit = false
    export let formCount
    export let buttonTambah = () => {}
    export let pemilikBeda = true
    let pemilikSamaClass = "sama"
    let jenis,merk,teknisi,keterangan 
    namaPemilik=alamat=notelp=jenis=merk=teknisi=keterangan=""
    
    $: cekFieldKosong(namaPemilik,alamat,notelp,jenis,merk,keterangan)
    function cekFieldKosong(namaPemilik,alamat,notelp,jenis,merk,keterangan){
        if(formCount == formID+1){
            if(pemilikBeda == false){
             if(namaPemilik != "" && alamat != "" && notelp != "" && jenis != "" && merk != ""  && keterangan != ""){
                adaygkosong = false
                }
        }else{
            if( jenis != "" && merk != "" && keterangan != "" ){
                adaygkosong = false
            }
        }
        }
       
    }
    let bukadaftarpel = () => {
        if(daridafpel !=null){
            formpemilikdisabled = ""
        iddafpel = ""
        daridafpel = null
        namaPemilik=alamat=notelp = ""
        }else{

            orkvisibility = true
        }

    }

    export let removeMe = ()  => {
        formCount--
        // nodeRef.parentNode.removeChild(nodeRef);
    }


    function bringBackPemilikForm(){
        pemilikBeda?pemilikBeda=false:pemilikBeda=true
        pemilikSamaClass = pemilikBeda? "sama":"beda"
    }


    if(edit){
        X.ajax({
            url:apiurl+"daftarmesin",
            type:"GET",
            data: {
                id: formID
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                jenis = result.data.jenis_mesin
                merk = result.data.merek
                //teknisi emang gabisa di load dan kayanya gaperlu juga...
                keterangan = result.data.keterangan
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
        
        buttonTambah = () =>  {
            if(jenis != "" && merk != "" && keterangan != ""){
                X.ajax({
                    url:apiurl+"updatemesin",
                    type:"POST",
                    data: {
                            id:formID,
                            jenis:jenis,
                            merek:merk,
                            teknisi:teknisi,
                            keterangan:keterangan
                    },
                    
                    beforeSend: function (request) {
                                    request.setRequestHeader('Authorization', getCookie('token'));
                                },
                    success: function(result,status){
                        if(status == "success")
                            {
                                bodynotif = "berhasil update data mesin"
                                responsestatus = "sukses"
                                triggernotif = "show"
                                setTimeout(function (){
                                        removeMe()    
                                    }, 2000);
                            }

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



<div bind:this={nodeRef} id="form_{formID}" class="dataGroup {pemilikSamaClass} {daridafpel}" style="display: flex; flex-direction:column; gap:20px;position: relative;">
    {#if formID != 0 || edit}
    <div style="display: flex; margin-bottom: -20px">
        {#if !pemilikBeda && !edit}
            <ButtonSmall style="margin-top: -50px; position: absolute; right: 40px; top: 2px" on:click={bringBackPemilikForm}><p style="font-size: 14px;
                color: white; font-weight: normal">Pemilik Sama</p></ButtonSmall>
        {/if}
        <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-top: -50px; position: absolute; right: 0" on:click={removeMe}/>
    </div>
    {/if}
    <!-- data pemilik -->
    {#if formID != 0 && pemilikBeda && !edit}
    <Card bind:this={pemilik} width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Pemilik Tidak Sama?</p>
            <ButtonSmall on:click={bringBackPemilikForm}> 
                <p style="font-size: 14px;
                color: white; font-weight: normal">
                Iya
                </p> 
            </ButtonSmall>
        </div>
    
    </Card>
    {:else if !pemilikBeda && !edit}
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Pemilik</p>
            <ButtonSmall on:click={()=>bukadaftarpel()}> 
                <p style="font-size: 14px;
                color: white; font-weight: normal">
                {teksbtndafpel}
                </p> 
            </ButtonSmall>
        </div>
        <form name="datapemilik">
            <InputText style="margin-top:16px" label="Pemilik" name="pemilik" value={namaPemilik} disabled={formpemilikdisabled}/>
            <InputText style="margin-top:16px; display:none" label="iddafpel" name="dafpel" value={iddafpel} disabled={formpemilikdisabled}/>
            <InputText style="margin-top:16px" label="Alamat" name="alamat" height="75" value={alamat} disabled={formpemilikdisabled}/>
            <InputText style="margin-top:16px" label="No. Telp" name="telp" value={notelp} disabled={formpemilikdisabled}/>
        </form>
        
    </Card>

    {:else if !edit}
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Pemilik</p>
            <ButtonSmall on:click={()=>bukadaftarpel()}> 
                <p style="font-size: 14px;
                color: white; font-weight: normal">
                {teksbtndafpel}
                </p> 
            </ButtonSmall>
        </div>
        <form name="datapemilik">
            <InputText style="margin-top:16px" label="Pemilik" name="pemilik" value={namaPemilik} disabled={formpemilikdisabled}/>
            <InputText style="margin-top:16px; display:none" label="iddafpel" name="dafpel" value={iddafpel} disabled={formpemilikdisabled}/>
            <InputText style="margin-top:16px" label="Alamat" name="alamat" height="75" value={alamat} disabled={formpemilikdisabled}/>
            <InputText style="margin-top:16px" label="No. Telp" name="telp" value={notelp} disabled={formpemilikdisabled}/>
        </form>
        
    </Card>
    {/if}
    <!-- data mesin -->
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Mesin</p>
           
        </div>
        <form name="datamesin">
            <InputText style="margin-top:16px" label="Jenis" name="jenis" bind:value={jenis}/>
            <InputText style="margin-top:16px" label="Merk" name="merk" bind:value={merk}/>
           
            <InputText style="margin-top:16px" label="Teknisi" name="teknisi" bind:value={teknisi} type="autocomplete"/>
            <InputText style="margin-top:16px" label="Ket." height="75" name="keterangan" bind:value={keterangan}/>
            {#if formCount <2}
            <ButtonSmall style="width: 100%; margin-top: 10px" on:click={buttonTambah}>

                {#if !edit}
                <p style="font-size: 20px;
                    color: white; font-weight: normal">
                    Tambahkan
                    </p>     
                {:else}
                <p style="font-size: 20px;
                color: white; font-weight: normal">
                Simpan
                </p> 
                {/if}
                
            </ButtonSmall>
            {/if}
        </form>
    </Card>
</div>

{#if orkvisibility}
    <ORK bind:visibility={orkvisibility} height=574 width=600 judul={"Daftar Pelanggan"}  visibilitytombolkonfirm={false} titleLanjutbtn="OKE">
        <Table editDetail = { () => edit('buka')}  bind:id={iddafpel} bind:name={namaPemilik} bind:orkvisibility={orkvisibility} bind:alamat={alamat} bind:notelp={notelp} tableHead={tableHead} dataTableCFG={dtblCFG} bind:daridafpel={daridafpel} context='Customer'/>
    </ORK>
{/if}

{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}