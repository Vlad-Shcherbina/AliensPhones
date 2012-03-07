reusable = [
    "Backdoors", # placeholder
    "Reset",
    "DebugProcess",
    "DamageLungs",

    "Queen",
    "Evolve",
    "Erupt",

    "UnbearablePain",
    "LiverWound",
    "IntestineWound",
    "LungWound",

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
    "LeftLeg",
    "RightLeg",

    "AbdominalSurgery",
    "RemovalSurgery",

    "VascularSurgeryTorso",
    "VascularSurgeryLeftArm",
    "VascularSurgeryRightArm",
    "VascularSurgeryLeftLeg",
    "VascularSurgeryRightLeg",

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
    "FybrinogenLocalRightArm",
    "FybrinogenLocalLeftLeg",
    "FybrinogenLocalRightLeg",

    "UrcaineTorso",
    "UrcaineLeftArm",
    "UrcaineRightArm",
    "UrcaineLeftLeg",
    "UrcaineRightLeg",

    "FirstAidKitTorso",
    "FirstAidKitLeftArm",
    "FirstAidKitRightArm",
    "FirstAidKitLeftLeg",
    "FirstAidKitRightLeg",

    "EssenceCita",
    "PlasmoDialite",

    "StreptonideTonine",
    "MonoFloxacyne",
    "Cyprostane",
    "Klodil",
    "Citosolute",
    "DichloFlu",

    "Perftoran",


    "Healer",
    "Requre",

    "Friz",

    "RandomEventEvil",
    "Facehugger",

    "GetAnalysis",


    "***break***",

    "PolyPiroCetam",
	
    "RandomEventGood",
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
