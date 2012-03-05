reusable = [
    "Backdoors", # placeholder
    "Reset",
    "DebugProcess",
    "DamageLungs",

    "UnbearablePain",
    "LiverWound",

    "Cross",
    "Safe",
    "Gasp",
    "Resp",
    "Mask",

    "Yellow",
    "Blue",

    "Torso",
    "LeftArm",
    "RightArm",
    "LeftLegDemo",
    "RightLegDemo",
]

unique = [
    #"Sambuca",

    "Surfaktant",
    "AntiAlvine",
    "PlasmaToxone",

    "Ehinospore",

    "Erpoiteen",

    "Warfareen",
    "WarfareenSalicylat",

    "BenzylAlienat",
    "MethMorthine",
    "MetanolCyanide",

    "FybrinogenLocalTorso",
    "FybrinogenLocalLeftArm",

    "UrcaineTorso",
    "UrcaineLeftArm",
    "UrcaineRightArm",

    "FirstAidKitTorso",
    "FirstAidKitLeftArm",
    "FirstAidKitRightArm",

    "EssenceCita",
    "PlasmoDialite",

    "StreptonideTonine",
    "MonoFloxacyne",
    "Cyprostane",
    "Klodil",
    "Citosolute",
    "DichloFlu",

    "Perftoran",

    "VascularSurgeryTorso",
    "VascularSurgeryLeftArm",
    "VascularSurgeryRightArm",

    "Healer",
    "Requre",

    "***break***",

    
    "AbdominalSurgery",
    "DichloFlu",
    "Facehugger",

    "FirstAidKitLeftLeg",
    "FirstAidKitRightLeg",

    "Friz",
    "FybrinogenLocalLeftLeg",
    "FybrinogenLocalRightArm",
    "FybrinogenLocalRightLeg",
    "GetAnalysis",
    "MetanolCyanide",
    "MethMorthine",
    "PolyPiroCetam",
    "RemovalSurgery",
    "UrcaineLeftLeg",
    "UrcaineRightLeg",
    "VascularSurgeryLeftLeg",
    "VascularSurgeryRightLeg",
	
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
