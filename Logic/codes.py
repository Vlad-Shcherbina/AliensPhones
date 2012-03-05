reusable = [
    "Backdoors", # placeholder
    "Reset",
    "DebugProcess",
    "DamageLungs",

    "UnbearablePain",

    "Cross",
    "Safe",
    "Gasp",
    "Resp",
    "Mask",

    "Yellow",
    "Blue",

    "LeftArm",
    "RightArmDemo",
    "LeftLegDemo",
    "RightLegDemo",
    "TorsoDemo",
]

unique = [
    "Sambuca",

    "Surfaktant",
    "AntiAlvine",
    "PlasmaToxone",

    "BenzylAlienat",
    "MethMorthine",
    "MetanolCyanide",
    "UrcaineLeftArm",

    "EssenceCita",
    "PlasmoDialite",

    "StreptonideTonine",
    "MonoFloxacyne",
    "Cyprostane",
    "Klodil",
    "Citosolute",
    "DichloFlu",

    "Erpoiteen",


    "***break***",

    
    "AbdominalSurgery",
    "DichloFlu",
    "Ehinospore",
    "Facehugger",
    "FirstAidKitLeftArm",
    "FirstAidKitLeftLeg",
    "FirstAidKitRightArm",
    "FirstAidKitRightLeg",
    "FirstAidKitTorso",
    "Friz",
    "FybrinogenLocalLeftArm",
    "FybrinogenLocalLeftLeg",
    "FybrinogenLocalRightArm",
    "FybrinogenLocalRightLeg",
    "FybrinogenLocalTorso",
    "GetAnalysis",
    "Healer",
    "MetanolCyanide",
    "MethMorthine",
    "Perftoran",
    "PolyPiroCetam",
    "RemovalSurgery",
    "Requre",
    "UrcaineLeftLeg",
    "UrcaineRightArm",
    "UrcaineRightLeg",
    "UrcaineTorso",
    "VascularSurgeryLeftArm",
    "VascularSurgeryLeftLeg",
    "VascularSurgeryRightArm",
    "VascularSurgeryRightLeg",
    "VascularSurgeryTorso",
    "Warfareen",
    "WarfareenSalicylat",
	
    "RandomEventGood",
    "RandomEventEvil",
]

"""
    
]
"""


def get_code_path(name):
    file_name = name
    if name in reusable:
        file_name = 'reusable/'+file_name
    file_name = '../../Cards/codes/'+file_name
    if file_name.endswith('Demo'):
        file_name = file_name[:-4]
    return file_name


def get_codes(name):
    return [c.strip() for c in open(get_code_path(name)).readlines()]

def phoneword(s):
    t = 'abc,def,ghi,jkl,mno,pqrs,tuv,wxyz'.split(',')
    result = ''
    for c in s:
        for i, key in enumerate(t, 2):
            if c in key:
                result += str(i)
                break
        else:
            result += c
    return result
